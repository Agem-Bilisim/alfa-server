package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import tr.com.agem.alfa.form.BaseForm;
import tr.com.agem.alfa.util.CommonUtils;

public class ControllerUtils {

	/**
	 * Generates error message string from specified binding result instances.
	 * 
	 * @param bindingResult
	 * @return
	 */
	public static String toErrorMessage(BindingResult bindingResult) {
		checkArgument(bindingResult != null, "Binding result cannot be null");
		List<ObjectError> errors = bindingResult.getAllErrors();
		StringBuilder errMsg = new StringBuilder();
		if (errors != null) {
			for (ObjectError error : errors) {
				// @formatter:off
				errMsg.append(error.getCode())
				.append(CommonUtils.SPACE_DELIM).append(error.getObjectName())
				.append(CommonUtils.SPACE_DELIM).append(error.getDefaultMessage())
				.append(CommonUtils.SPACE_DELIM);
				// @formatter:on
			}
		}
		return errMsg.toString();
	}

	public static String getRedirectMapping(BaseForm form, String orElse) {
		String url = form != null && form.getRedirect() != null && !form.getRedirect().isEmpty() ? form.getRedirect()
				: orElse;
		return "redirect:" + (url.startsWith("/") ? url : "/" + url);
	}

	public static String getRedirectMapping(String redirectUrl, String orElse) {
		String url = redirectUrl != null && !redirectUrl.isEmpty() ? redirectUrl : orElse;
		return "redirect:" + (url.startsWith("/") ? url : "/" + url);
	}

	public static String getRedirectUrl(String redirectUrl, String orElse) {
		return redirectUrl != null && !redirectUrl.isEmpty() ? redirectUrl : orElse;
	}

}
