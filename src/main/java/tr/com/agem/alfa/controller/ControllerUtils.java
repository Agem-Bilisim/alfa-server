package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

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

}
