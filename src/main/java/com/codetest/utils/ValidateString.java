/**
 * 
 */
package com.codetest.utils;

import javax.validation.Payload;

/**
 * @author Balarkan Babith
 *
 */
public @interface ValidateString {

    String[] acceptedValues();

    String message() default "{Value is not valid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { }; 
}