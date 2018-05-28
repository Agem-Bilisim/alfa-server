package tr.com.agem.alfa.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import tr.com.agem.alfa.security.CustomAuthenticationProvider;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Configuration
@EnableWebSecurity // handles CSRF
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	// @formatter:off
	public static final String[] ANON_URLS = { "/img/**", "/css/**", "/fonts/**", "/plugins/**", "/js/**",
			"/agent/sysinfo-result/**", "/agent/survey-result/**", };
	public static final String[] NON_CSRF_URLS = { "/agent/sysinfo-result/**", "/agent/survey-result/**" };
	// @formatter:on

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers(ANON_URLS).permitAll().anyRequest().fullyAuthenticated().and().csrf()
				.ignoringAntMatchers(NON_CSRF_URLS).and().formLogin().loginPage("/login").failureUrl("/login?error")
				.defaultSuccessUrl("/home", true).permitAll().and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll()
				.and().rememberMe().and().headers().cacheControl();
		// @formatter:on
	}

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		return new SysLogFilter();
	}

	/**
	 * Log incoming requests.
	 * 
	 * @author emre
	 *
	 */
	public class SysLogFilter extends CommonsRequestLoggingFilter {

		List<RequestMatcher> matchers = new ArrayList<RequestMatcher>();

		public SysLogFilter() {
			for (String url : SecurityConfig.ANON_URLS) {
				matchers.add(new AntPathRequestMatcher(url, null));
			}
			this.setIncludeQueryString(true);
			this.setIncludePayload(true);
			this.setBeforeMessagePrefix(" Request: [");
		}

		@Override
		protected boolean shouldLog(HttpServletRequest request) {
			return logger.isDebugEnabled() && !isAnonRequest(request);
		}

		private boolean isAnonRequest(HttpServletRequest request) {
			for (RequestMatcher matcher : matchers) {
				if (matcher.matches(request))
					return true;
			}
			return false;
		}
		
		@Override
		protected void beforeRequest(HttpServletRequest request, String message) {
			logger.debug(request.getMethod().toUpperCase() + message);
		}

		@Override
		protected void afterRequest(HttpServletRequest request, String message) {
		}

	}

}
