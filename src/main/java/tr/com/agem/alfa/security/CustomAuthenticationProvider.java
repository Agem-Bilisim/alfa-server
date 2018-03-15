package tr.com.agem.alfa.security;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.User;
import tr.com.agem.alfa.service.UserService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	private UserService userService;

	public static final int ENCODER_STRENGTH = 11;
	private static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	public CustomAuthenticationProvider(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder(ENCODER_STRENGTH);
	}

	@Override
	@Transactional(readOnly = true)
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		String userName = auth.getName();
		if (!(auth.getCredentials() instanceof String)) {
			String message = String.format("Credential class was not String: %s",
					auth.getCredentials().getClass().getName());
			throw new BadCredentialsException(message);
		}
		String password = auth.getCredentials().toString();

		User user = null;

		try {
			user = userService.findByUserNameOrEmail(userName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (user == null) {
			String message = String.format("User %s was not found.", userName);
			log.warn(message);
			throw new BadCredentialsException(message);
		}

		boolean authenticated = false;
		log.debug("Authenticating against database.");
		if (!encoder().matches(password, user.getPasswordHash())) {
			String message = String.format("User %s was not found.", userName);
			log.warn(message);
			throw new BadCredentialsException(message);
		}
		authenticated = true;
		log.info("Authenticated user: {}", userName);

		if (authenticated) {
			// Determine role and privileges
			Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
			return new UsernamePasswordAuthenticationToken(new CurrentUser(user, authorities), password, authorities);
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		checkArgument(user != null, "User cannot be null.");
		checkArgument(user.getRole() != null && !isNullOrEmpty(user.getRole().getName()),
				"Role and its name cannot be null or empty.");
		checkArgument(user.getRole() != null && user.getRole().getId() != null, "Role and its id cannot be null.");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// Add role authority
		authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getName().toUpperCase(Locale.ENGLISH)));
		return authorities;
	}

}
