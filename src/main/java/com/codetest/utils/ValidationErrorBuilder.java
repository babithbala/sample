/**
 * 
 */
package com.codetest.utils;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * @author Balarkan Babith
 *
 */
public class ValidationErrorBuilder {
	
	private static final Logger LOGGER = Logger.getLogger(ValidationErrorBuilder.class.getName());

	public static ValidationError fromBindingErrors(Errors errors) {
		ValidationError error = new ValidationError("Validation error occured. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
