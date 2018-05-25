package tr.com.agem.alfa.validator;

import static com.google.common.base.Strings.isNullOrEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tr.com.agem.alfa.form.LdapIntegrationForm;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
public class LdapIntegrationFormValidator implements Validator {

	private static final Logger log = LoggerFactory.getLogger(LdapIntegrationFormValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(LdapIntegrationForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Validating {}", target);
		LdapIntegrationForm form = (LdapIntegrationForm) target;
		validatePasswords(errors, form);
		validateDnPatternOrFilter(errors, form);
	}

	private void validateDnPatternOrFilter(Errors errors, LdapIntegrationForm form) {
		if (isNullOrEmpty(form.getUserDnPattern()) && isNullOrEmpty(form.getSearchFilter())) {
			errors.reject("dnpattern.and.filter.missing", "User DN pattern or search filter must be provided.");
		}
	}

	private void validatePasswords(Errors errors, LdapIntegrationForm form) {
		if (!form.getPassword().equals(form.getPasswordRepeated())) {
			errors.reject("password.nomatch", "Passwords do not match");
		}
	}

}
