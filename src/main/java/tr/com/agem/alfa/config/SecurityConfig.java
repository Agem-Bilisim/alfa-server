package tr.com.agem.alfa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
	private static final String[] ANON_URLS = {
			"/img/**", 
			"/webjars/**", 
			"/css/**", 
			"/fonts/**", 
			"/plugins/**", 
			"/js/**",
			"/agent/register/**",
			"/agent/task-status/**",
			"/agent/session/**",
			"/agent/missing-plugin/**"
	};
	private static final String[] NON_CSRF_URLS = {
			"/agent/sysinfo-result/**",
			"/agent/survey-result/**",
	};
	// @formatter:on

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http.authorizeRequests()
                .antMatchers(ANON_URLS).permitAll()
                .anyRequest().fullyAuthenticated()
            .and()
            	.csrf()
            	.ignoringAntMatchers(NON_CSRF_URLS)
            .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            .and()
                .rememberMe()
            .and()
            	.headers()
            	.cacheControl();
        // @formatter:on
	}

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

}
