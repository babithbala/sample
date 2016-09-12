/**
 * 
 */
package com.codetest.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codetest.generated.Product;
import com.codetest.generated.Trade;


/**
 * @author Balarkan Babith
 *
 */
public class ProductValidator implements Validator{

	private TradeInfoValidator tradeInfoValidator;
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product)target;
		tradeInfoValidator = new TradeInfoValidator();
		validateOption(product,errors);
		validateProductType(product,errors);
		validateCounterparty(product,errors);
		invokeTradeInfoValidation(product,errors);
	}
	
	private void validateOption(Product product,Errors errors){
		if(product.getOption()!=null && 
				(!product.getOption().equals("BUY") && 
				!product.getOption().equals("SELL") )){
				
				errors.reject("option","Invalid Option- Valid values are BUY  or SELL");
			}
	}
	
	private void validateProductType(Product product,Errors errors){
		if(product.getProductType()!=null && 
				!product.getProductType().equals("CURRENCY") && 
				!product.getProductType().equals("TRAVEL_CARD") && 
				! product.getProductType().equals("TRANSFER")  ){
				
				errors.reject("productType","Invalid ProductType- Valid values are CURRENCY , TRAVEL_CARD or TRANSFER");
			}
	}
	
	private void validateCounterparty(Product product,Errors errors){
		if(product.getCounterparty()!=null &&
				! product.getCounterparty().equals("PLUTO1") &&  
				! product.getCounterparty().equals("PLUTO2")){
			errors.reject("counterparty","Invalid Counterparty");
		}
	}
	
	private void invokeTradeInfoValidation(Product product,Errors errors){
		errors.pushNestedPath("tradeInfo");
        ValidationUtils.invokeValidator(this.tradeInfoValidator, product, errors);
        errors.popNestedPath();
	}

}
