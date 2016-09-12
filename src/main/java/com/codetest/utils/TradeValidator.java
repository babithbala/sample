/**
 * 
 */
package com.codetest.utils;

import java.util.Calendar;
import java.util.Currency;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codetest.generated.Product;
import com.codetest.generated.Trade;

/**
 * @author Balarkan Babith
 *
 */
public class TradeValidator implements Validator{

	private static final Logger LOGGER = Logger.getLogger(TradeValidator.class.getName());
	private ProductValidator productValidator; 
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Trade.class.isAssignableFrom(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Trade trade = (Trade) target;
		productValidator = new ProductValidator();
		LOGGER.info("validate-----");
		// ValidationUtils.rejectIfEmpty(errors, "title", "products.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
//		if(trade.getProducts().size() > 0){
//			//errors.rejectValue("products","error.products.size");
//			validateProduct(trade.getProducts().get(0), errors);
//		}
		int index = 0 ;
		for (Product product : trade.getProducts()){
			errors.pushNestedPath("products[" + index + "]");
            ValidationUtils.invokeValidator(this.productValidator, product, errors);
            errors.popNestedPath();
            index++;
		}
	}
	
	/* (non-Javadoc)
	 * 
	 */
	private static boolean validateCurrencyCode(@NotNull final String currencyCode){
		 	try
		 	{
		 		Currency.getInstance(currencyCode);
		 		return true;
		 	}
		 	catch (final IllegalArgumentException x){
		 		return false;
		 	}
	 }
	 
	/* (non-Javadoc)
	 * check if weekend
	 */
	 private boolean isWeekendDay(Calendar cal){
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
					cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				return true;
			}else
				return false;
	 }
	 
	 /* (non-Javadoc)
		 * valueDate validation against tradeDate
		 */
	 private static boolean validateValueDate(final String valueDate){
		 return false;
	 }

}
