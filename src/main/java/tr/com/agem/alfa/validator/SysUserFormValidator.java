package tr.com.agem.alfa.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tr.com.agem.alfa.form.SysUserForm;
import tr.com.agem.alfa.model.SysUser;
import tr.com.agem.alfa.service.SysUserService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
public class SysUserFormValidator implements Validator {

	private static final Logger log = LoggerFactory.getLogger(SysUserFormValidator.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(SysUserForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Validating {}", target);
		SysUserForm form = (SysUserForm) target;
		validatePasswords(errors, form);
		validateEmail(errors, form);
	}
	
	private void validatePasswords(Errors errors, SysUserForm form) {
		if (!form.getPassword().equals(form.getPasswordRepeated())) {
			errors.reject("password.nomatch", "Parola ve tekrarı aynı değil.");
		}
	}
	
	private void validateEmail(Errors errors, SysUserForm form) {
		SysUser user = null;
		try {
			user = sysUserService.getUserByEmail(form.getEmail());
		} catch (EmptyResultDataAccessException e) {
		}
		if (user != null && (form.getId() == null || (!user.getEmail().equals(form.getEmail())))) {
			errors.reject("email.exists", "E-posta adresiyle kullanıcı zaten mevcut.");
		}
	}

}
