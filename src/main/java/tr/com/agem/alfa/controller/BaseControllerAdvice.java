package tr.com.agem.alfa.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import tr.com.agem.alfa.model.CurrentUser;

/**
 * Provides application version, date format and authenticated user info to
 * controllers.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@ControllerAdvice
public class BaseControllerAdvice {

	@Value("${sys.version}")
	private String applicationVersion;

	@Value("${sys.date-format}")
	private String applicationDateFormat;

	@Value("${sys.datetime-format}")
	private String applicationDatetimeFormat;

	@ModelAttribute("currentUser")
	public CurrentUser getCurrentUser(Authentication authentication) {
		return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
	}

	@ModelAttribute("applicationVersion")
	public String getApplicationVersion() {
		return applicationVersion;
	}

	@ModelAttribute("applicationDateFormat")
	public String getApplicationDateFormat() {
		return applicationDateFormat;
	}

	@ModelAttribute("applicationDatetimeFormat")
	public String getApplicationDatetimeFormat() {
		return applicationDatetimeFormat;
	}

}
