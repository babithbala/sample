/**
 * 
 */
package com.codetest.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codetest.generated.CurrencyHolidays;
import com.codetest.generated.EUR;
import com.codetest.generated.Product;
import com.codetest.generated.Trade;
import com.codetest.generated.TradeInfo;
import com.codetest.generated.USD;
import com.codetest.generated.ValidDate;
import com.codetest.generated.TradeInfo.TradeType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;


/**
 * @author Balarkan Babith
 *
 */
public class TradeInfoValidator implements Validator{
	
	private static final Logger LOGGER = Logger.getLogger(TradeInfoValidator.class.getName());
			
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
	public void validate(Object target, Errors errors) {
		TradeInfo tradeInfo = ((Product)target).getTradeInfo();
		
		try {
			validateValueDate(tradeInfo,errors);
			isValidTOCurrencyCode(tradeInfo.getTo(),errors);
			isValidFROMCurrencyCode(tradeInfo.getFrom(),errors);
			
			validateTradeType(tradeInfo,errors);
			validateValueDateAgainstProductType(tradeInfo,
					((Product)target).getProductType(),errors);
			validateTradeStyleForOption(tradeInfo,errors);
		} catch (IOException e) {
			LOGGER.info("Exception "+e.getMessage());
		}
		
	}

	private void validateValueDate(TradeInfo tradeInfo, Errors errors) 
			throws JsonParseException, JsonMappingException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			Date valueDate = sdf.parse(tradeInfo.getValueDate());
		
			Date tradeDate = sdf.parse(tradeInfo.getTradeDate());
			if(! (valueDate.compareTo(tradeDate)>0)){
				errors.reject("valueDate","Value date cannot be before trade date");
        	}
			Calendar cal = Calendar.getInstance();
			cal.setTime(valueDate);
			if( isWeekendDay(cal) ){
				errors.reject("valueDate","Value date cannot be a weekend date");
			}
			isCurrencyHoliday(tradeInfo.getTo(), valueDate,errors);
			isCurrencyHoliday(tradeInfo.getFrom(), valueDate,errors);
			
    	} catch (ParseException e) {
			LOGGER.info("Exception "+e.getMessage());
		}		
	}
	
	private void validateTradeStyleForOption(TradeInfo tradeInfo,Errors errors){
		if(tradeInfo.getTradeType()!=null &&
				 tradeInfo.getTradeType().equals("OPTION") &&
				 tradeInfo.getTradeStyle()!=null && (
				 ! tradeInfo.getTradeStyle().equals("USD") && 
				 ! tradeInfo.getTradeStyle().equals("EUR"))){
			errors.reject("tradeStyle","Trade style can be wither USD or EUR for OPTION");
		}
		validateForAmericanStyle(tradeInfo,errors);
		validateExpiryDate(tradeInfo,errors);
		validatePremiumDate(tradeInfo,errors);
	}
	
	private void validateForAmericanStyle(TradeInfo tradeInfo,Errors errors){
		if(tradeInfo.getTradeType()!=null &&
				 tradeInfo.getTradeType().equals("OPTION") &&
				 tradeInfo.getTradeStyle()!=null && 
				 tradeInfo.getTradeStyle().equals("USD")){
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    	try {
				if(tradeInfo.getExcerciseStartDate()!=null) {
					Date excerciseStartDate = sdf.parse(tradeInfo.getExcerciseStartDate());
					
					Date tradeDate = sdf.parse(tradeInfo.getTradeDate());
					Date expiryDate = sdf.parse(tradeInfo.getExpiryDate());
					
					if( (!(excerciseStartDate.compareTo(tradeDate)>0)) && 
							(! (excerciseStartDate.compareTo(expiryDate)<0))){
						errors.reject("excerciseStartDate","ExcerciseStartDate has to be after the trade date but before the expiry date");
					}
				}else{
					errors.reject("excerciseStartDate","ExcerciseStartDate is required");
				}
			
	    	}catch (ParseException e) {
	    			LOGGER.info("Exception "+e.getMessage());
	        	}
		}
	}
	
	private void validateValueDateAgainstProductType(TradeInfo tradeInfo,String productType,Errors errors){
		 if(tradeInfo.getTradeType()!=null &&
				 ( tradeInfo.getTradeType().equals("SPOT") || 
				  tradeInfo.getTradeType().equals("FORWARD")) &&
				 productType!=null && productType.equals("CURRENCY")){
			 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			 //for CURRENCY value date is after two days
		    	try {
					Date valueDate = sdf.parse(tradeInfo.getValueDate());
				
					Date tradeDate = sdf.parse(tradeInfo.getTradeDate());
					LOGGER.info("ggg "+valueDate.compareTo(tradeDate));
					
					long diff = valueDate.getTime() - tradeDate.getTime();
					long diffDays = diff / (24 * 60 * 60 * 1000);
					if( diffDays<=2){
						errors.reject("valueDate","Value date is after 2 days from trading date");
		        	}
		    	} catch (ParseException e) {
					LOGGER.info("Exception "+e.getMessage());
				}	
		 }
	}
	
	private void validateExpiryDate(TradeInfo tradeInfo,Errors errors){
		if(tradeInfo.getTradeType()!=null &&
				( tradeInfo.getTradeType().equals("SPOT") || 
						  tradeInfo.getTradeType().equals("FORWARD"))  &&
				 tradeInfo.getPremiumDate()!=null && 
				 tradeInfo.getExpiryDate()!=null){
			 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			 try {
				 	Date expiryDate = sdf.parse(tradeInfo.getExpiryDate());
					
					Date deliveryDate = sdf.parse(tradeInfo.getDeliveryDate());
					
					if( (expiryDate.compareTo(deliveryDate)<0)) {
						errors.reject("expiryDate","Expiry date can not be before delivery date for SPOT and FORWORD");
					}
		    	} catch (ParseException e) {
					LOGGER.info("Exception "+e.getMessage());
				}	
		 }
	}
	
	private void validatePremiumDate(TradeInfo tradeInfo,Errors errors){
		if(tradeInfo.getTradeType()!=null &&
				( tradeInfo.getTradeType().equals("SPOT") || 
						  tradeInfo.getTradeType().equals("FORWARD"))   &&
				 tradeInfo.getPremiumDate()!=null && 
				 tradeInfo.getDeliveryDate()!=null){
			 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			 try {
					Date premiumDate = sdf.parse(tradeInfo.getPremiumDate());
				
					Date deliveryDate = sdf.parse(tradeInfo.getDeliveryDate());
					if( (premiumDate.compareTo(deliveryDate)<0)) {
						errors.reject("premiumDate","Premium date can not be before delivery date  for SPOT and FORWORD");
					}
					
		    	} catch (ParseException e) {
					LOGGER.info("Exception "+e.getMessage());
				}	
		 }
	}
	
	private boolean validateCurrencyCode(@NotNull final String currencyCode,Errors errors){
	 	try
	 	{
	 		Currency.getInstance(currencyCode);
	 		return true;
	 	}
	 	catch (final IllegalArgumentException x){
	 		errors.reject("to","Invalid Currency code");
	 		return false;
	 	}
	}
 
	 private boolean isWeekendDay(Calendar cal){
			// check if weekend
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
					cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				return true;
			}else
				return false;
	 }
	 
	 private void isCurrencyHoliday(String currencyCode, Date valueDate,Errors errors)
			 throws JsonParseException, JsonMappingException, IOException, ParseException{
		 ObjectMapper mapper = new ObjectMapper();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 String jsonString ="{\"title\":\"currency_holidays\",\"description\":\"currency_holidays_calendar_description\",\"holidays\":{\"EUR\":[{\"year\":\"2016\",\"validDates\":[{\"title\":\"New Years Day\",\"date\":\"01/01/2016\"},{\"title\":\"Good Friday\",\"date\":\"25/03/2016\"},{\"title\":\"Easter Monday\",\"date\":\"28/03/2016\"},{\"title\":\"Christmas Day\",\"date\":\"26/12/2016\"}]}],\"USD\":[{\"year\":\"2016\",\"validDates\":[{\"title\":\"New Years Day\",\"date\":\"01/01/2016\"},{\"title\":\"Martin Luther King Day\",\"date\":\"18/01/2016\"},{\"title\":\"Presidents Day\",\"date\":\"15/02/2016\"},{\"title\":\"Memorial Day\",\"date\":\"30/05/2016\"},{\"title\":\"Independence Day\",\"date\":\"04/07/2016\"},{\"title\":\"Labour Day\",\"date\":\"05/09/2016\"},{\"title\":\"Columbus Day\",\"date\":\"10/10/2016\"},{\"title\":\"Veterans Day\",\"date\":\"11/11/2016\"},{\"title\":\"Thanksgiving Day\",\"date\":\"24/12/2016\"},{\"title\":\"Christmas Day\",\"date\":\"26/12/2016\"}]}]}}";
		 //ClassLoader classLoader = getClass().getClassLoader();
		 CurrencyHolidays obj = mapper.readValue(jsonString,CurrencyHolidays.class);
		 if(currencyCode!=null && currencyCode.equals("EUR")){
			for(EUR euro : obj.getHolidays().getEUR()){//.get(1).getValidDates().get(1).get;
				for (ValidDate holiday:  euro.getValidDates()){
					if(sdf.parse(holiday.getDate()).compareTo(valueDate) ==0){
						errors.reject("valueDate","Value date is an EURO holiday");
					}
				}
			
			}
		 }
		 if(currencyCode!=null && currencyCode.equals("USD")){
				for(USD dollar : obj.getHolidays().getUSD()){//.get(1).getValidDates().get(1).get;
					for (ValidDate holiday:  dollar.getValidDates()){
						if(sdf.parse(holiday.getDate()).compareTo(valueDate) ==0){
							errors.reject("valueDate","Value date is a Dollar holiday");
						}
					}
				
				}
			 }
	 }
	 
	 private void isValidTOCurrencyCode(String currencyCode,Errors errors) throws MalformedURLException{
		 ObjectMapper mapper = new ObjectMapper();
		String jsonString= "{\"AED\":\"United Arab Emirates Dirham\",\"AFN\":\"Afghan Afghani\",\"ALL\":\"Albanian Lek\",\"AMD\":\"Armenian Dram\",\"ANG\":\"Netherlands Antillean Guilder\",\"AOA\":\"Angolan Kwanza\",\"ARS\":\"Argentine Peso\",\"AUD\":\"Australian Dollar\",\"AWG\":\"Aruban Florin\",\"AZN\":\"Azerbaijani Manat\",\"BAM\":\"Bosnia-Herzegovina Convertible Mark\",\"BBD\":\"Barbadian Dollar\",\"BDT\":\"Bangladeshi Taka\",\"BGN\":\"Bulgarian Lev\",\"BHD\":\"Bahraini Dinar\",\"BIF\":\"Burundian Franc\",\"BMD\":\"Bermudan Dollar\",\"BND\":\"Brunei Dollar\",\"BOB\":\"Bolivian Boliviano\",\"BRL\":\"Brazilian Real\",\"BSD\":\"Bahamian Dollar\",\"BTC\":\"Bitcoin\",\"BTN\":\"Bhutanese Ngultrum\",\"BWP\":\"Botswanan Pula\",\"BYN\":\"Belarusian Ruble\",\"BYR\":\"Belarusian Ruble (pre-2016)\",\"BZD\":\"Belize Dollar\",\"CAD\":\"Canadian Dollar\",\"CDF\":\"Congolese Franc\",\"CHF\":\"Swiss Franc\",\"CLF\":\"Chilean Unit of Account (UF)\",\"CLP\":\"Chilean Peso\",\"CNY\":\"Chinese Yuan\",\"COP\":\"Colombian Peso\",\"CRC\":\"Costa Rican Colón\",\"CUC\":\"Cuban Convertible Peso\",\"CUP\":\"Cuban Peso\",\"CVE\":\"Cape Verdean Escudo\",\"CZK\":\"Czech Republic Koruna\",\"DJF\":\"Djiboutian Franc\",\"DKK\":\"Danish Krone\",\"DOP\":\"Dominican Peso\",\"DZD\":\"Algerian Dinar\",\"EEK\":\"Estonian Kroon\",\"EGP\":\"Egyptian Pound\",\"ERN\":\"Eritrean Nakfa\",\"ETB\":\"Ethiopian Birr\",\"EUR\":\"Euro\",\"FJD\":\"Fijian Dollar\",\"FKP\":\"Falkland Islands Pound\",\"GBP\":\"British Pound Sterling\",\"GEL\":\"Georgian Lari\",\"GGP\":\"Guernsey Pound\",\"GHS\":\"Ghanaian Cedi\",\"GIP\":\"Gibraltar Pound\",\"GMD\":\"Gambian Dalasi\",\"GNF\":\"Guinean Franc\",\"GTQ\":\"Guatemalan Quetzal\",\"GYD\":\"Guyanaese Dollar\",\"HKD\":\"Hong Kong Dollar\",\"HNL\":\"Honduran Lempira\",\"HRK\":\"Croatian Kuna\",\"HTG\":\"Haitian Gourde\",\"HUF\":\"Hungarian Forint\",\"IDR\":\"Indonesian Rupiah\",\"ILS\":\"Israeli New Sheqel\",\"IMP\":\"Manx pound\",\"INR\":\"Indian Rupee\",\"IQD\":\"Iraqi Dinar\",\"IRR\":\"Iranian Rial\",\"ISK\":\"Icelandic Króna\",\"JEP\":\"Jersey Pound\",\"JMD\":\"Jamaican Dollar\",\"JOD\":\"Jordanian Dinar\",\"JPY\":\"Japanese Yen\",\"KES\":\"Kenyan Shilling\",\"KGS\":\"Kyrgystani Som\",\"KHR\":\"Cambodian Riel\",\"KMF\":\"Comorian Franc\",\"KPW\":\"North Korean Won\",\"KRW\":\"South Korean Won\",\"KWD\":\"Kuwaiti Dinar\",\"KYD\":\"Cayman Islands Dollar\",\"KZT\":\"Kazakhstani Tenge\",\"LAK\":\"Laotian Kip\",\"LBP\":\"Lebanese Pound\",\"LKR\":\"Sri Lankan Rupee\",\"LRD\":\"Liberian Dollar\",\"LSL\":\"Lesotho Loti\",\"LTL\":\"Lithuanian Litas\",\"LVL\":\"Latvian Lats\",\"LYD\":\"Libyan Dinar\",\"MAD\":\"Moroccan Dirham\",\"MDL\":\"Moldovan Leu\",\"MGA\":\"Malagasy Ariary\",\"MKD\":\"Macedonian Denar\",\"MMK\":\"Myanma Kyat\",\"MNT\":\"Mongolian Tugrik\",\"MOP\":\"Macanese Pataca\",\"MRO\":\"Mauritanian Ouguiya\",\"MTL\":\"Maltese Lira\",\"MUR\":\"Mauritian Rupee\",\"MVR\":\"Maldivian Rufiyaa\",\"MWK\":\"Malawian Kwacha\",\"MXN\":\"Mexican Peso\",\"MYR\":\"Malaysian Ringgit\",\"MZN\":\"Mozambican Metical\",\"NAD\":\"Namibian Dollar\",\"NGN\":\"Nigerian Naira\",\"NIO\":\"Nicaraguan Córdoba\",\"NOK\":\"Norwegian Krone\",\"NPR\":\"Nepalese Rupee\",\"NZD\":\"New Zealand Dollar\",\"OMR\":\"Omani Rial\",\"PAB\":\"Panamanian Balboa\",\"PEN\":\"Peruvian Nuevo Sol\",\"PGK\":\"Papua New Guinean Kina\",\"PHP\":\"Philippine Peso\",\"PKR\":\"Pakistani Rupee\",\"PLN\":\"Polish Zloty\",\"PYG\":\"Paraguayan Guarani\",\"QAR\":\"Qatari Rial\",\"RON\":\"Romanian Leu\",\"RSD\":\"Serbian Dinar\",\"RUB\":\"Russian Ruble\",\"RWF\":\"Rwandan Franc\",\"SAR\":\"Saudi Riyal\",\"SBD\":\"Solomon Islands Dollar\",\"SCR\":\"Seychellois Rupee\",\"SDG\":\"Sudanese Pound\",\"SEK\":\"Swedish Krona\",\"SGD\":\"Singapore Dollar\",\"SHP\":\"Saint Helena Pound\",\"SLL\":\"Sierra Leonean Leone\",\"SOS\":\"Somali Shilling\",\"SRD\":\"Surinamese Dollar\",\"STD\":\"São Tomé and Príncipe Dobra\",\"SVC\":\"Salvadoran Colón\",\"SYP\":\"Syrian Pound\",\"SZL\":\"Swazi Lilangeni\",\"THB\":\"Thai Baht\",\"TJS\":\"Tajikistani Somoni\",\"TMT\":\"Turkmenistani Manat\",\"TND\":\"Tunisian Dinar\",\"TOP\":\"Tongan Pa?anga\",\"TRY\":\"Turkish Lira\",\"TTD\":\"Trinidad and Tobago Dollar\",\"TWD\":\"New Taiwan Dollar\",\"TZS\":\"Tanzanian Shilling\",\"UAH\":\"Ukrainian Hryvnia\",\"UGX\":\"Ugandan Shilling\",\"USD\":\"United States Dollar\",\"UYU\":\"Uruguayan Peso\",\"UZS\":\"Uzbekistan Som\",\"VEF\":\"Venezuelan Bolívar Fuerte\",\"VND\":\"Vietnamese Dong\",\"VUV\":\"Vanuatu Vatu\",\"WST\":\"Samoan Tala\",\"XAF\":\"CFA Franc BEAC\",\"XAG\":\"Silver Ounce\",\"XAU\":\"Gold Ounce\",\"XCD\":\"East Caribbean Dollar\",\"XDR\":\"Special Drawing Rights\",\"XOF\":\"CFA Franc BCEAO\",\"XPD\":\"Palladium Ounce\",\"XPF\":\"CFP Franc\",\"XPT\":\"Platinum Ounce\",\"YER\":\"Yemeni Rial\",\"ZAR\":\"South African Rand\",\"ZMK\":\"Zambian Kwacha (pre-2013)\",\"ZMW\":\"Zambian Kwacha\",\"ZWL\":\"Zimbabwean Dollar\"}";
		JSONObject jsonObject = new JSONObject(jsonString);
		 if(currencyCode!=null && !jsonObject.has(currencyCode)){
			 errors.reject("to","Invalid TO Currency code");
		 }
	 }
	 private void isValidFROMCurrencyCode(String currencyCode,Errors errors) throws MalformedURLException{
		 ObjectMapper mapper = new ObjectMapper();
			String jsonString= "{\"AED\":\"United Arab Emirates Dirham\",\"AFN\":\"Afghan Afghani\",\"ALL\":\"Albanian Lek\",\"AMD\":\"Armenian Dram\",\"ANG\":\"Netherlands Antillean Guilder\",\"AOA\":\"Angolan Kwanza\",\"ARS\":\"Argentine Peso\",\"AUD\":\"Australian Dollar\",\"AWG\":\"Aruban Florin\",\"AZN\":\"Azerbaijani Manat\",\"BAM\":\"Bosnia-Herzegovina Convertible Mark\",\"BBD\":\"Barbadian Dollar\",\"BDT\":\"Bangladeshi Taka\",\"BGN\":\"Bulgarian Lev\",\"BHD\":\"Bahraini Dinar\",\"BIF\":\"Burundian Franc\",\"BMD\":\"Bermudan Dollar\",\"BND\":\"Brunei Dollar\",\"BOB\":\"Bolivian Boliviano\",\"BRL\":\"Brazilian Real\",\"BSD\":\"Bahamian Dollar\",\"BTC\":\"Bitcoin\",\"BTN\":\"Bhutanese Ngultrum\",\"BWP\":\"Botswanan Pula\",\"BYN\":\"Belarusian Ruble\",\"BYR\":\"Belarusian Ruble (pre-2016)\",\"BZD\":\"Belize Dollar\",\"CAD\":\"Canadian Dollar\",\"CDF\":\"Congolese Franc\",\"CHF\":\"Swiss Franc\",\"CLF\":\"Chilean Unit of Account (UF)\",\"CLP\":\"Chilean Peso\",\"CNY\":\"Chinese Yuan\",\"COP\":\"Colombian Peso\",\"CRC\":\"Costa Rican Colón\",\"CUC\":\"Cuban Convertible Peso\",\"CUP\":\"Cuban Peso\",\"CVE\":\"Cape Verdean Escudo\",\"CZK\":\"Czech Republic Koruna\",\"DJF\":\"Djiboutian Franc\",\"DKK\":\"Danish Krone\",\"DOP\":\"Dominican Peso\",\"DZD\":\"Algerian Dinar\",\"EEK\":\"Estonian Kroon\",\"EGP\":\"Egyptian Pound\",\"ERN\":\"Eritrean Nakfa\",\"ETB\":\"Ethiopian Birr\",\"EUR\":\"Euro\",\"FJD\":\"Fijian Dollar\",\"FKP\":\"Falkland Islands Pound\",\"GBP\":\"British Pound Sterling\",\"GEL\":\"Georgian Lari\",\"GGP\":\"Guernsey Pound\",\"GHS\":\"Ghanaian Cedi\",\"GIP\":\"Gibraltar Pound\",\"GMD\":\"Gambian Dalasi\",\"GNF\":\"Guinean Franc\",\"GTQ\":\"Guatemalan Quetzal\",\"GYD\":\"Guyanaese Dollar\",\"HKD\":\"Hong Kong Dollar\",\"HNL\":\"Honduran Lempira\",\"HRK\":\"Croatian Kuna\",\"HTG\":\"Haitian Gourde\",\"HUF\":\"Hungarian Forint\",\"IDR\":\"Indonesian Rupiah\",\"ILS\":\"Israeli New Sheqel\",\"IMP\":\"Manx pound\",\"INR\":\"Indian Rupee\",\"IQD\":\"Iraqi Dinar\",\"IRR\":\"Iranian Rial\",\"ISK\":\"Icelandic Króna\",\"JEP\":\"Jersey Pound\",\"JMD\":\"Jamaican Dollar\",\"JOD\":\"Jordanian Dinar\",\"JPY\":\"Japanese Yen\",\"KES\":\"Kenyan Shilling\",\"KGS\":\"Kyrgystani Som\",\"KHR\":\"Cambodian Riel\",\"KMF\":\"Comorian Franc\",\"KPW\":\"North Korean Won\",\"KRW\":\"South Korean Won\",\"KWD\":\"Kuwaiti Dinar\",\"KYD\":\"Cayman Islands Dollar\",\"KZT\":\"Kazakhstani Tenge\",\"LAK\":\"Laotian Kip\",\"LBP\":\"Lebanese Pound\",\"LKR\":\"Sri Lankan Rupee\",\"LRD\":\"Liberian Dollar\",\"LSL\":\"Lesotho Loti\",\"LTL\":\"Lithuanian Litas\",\"LVL\":\"Latvian Lats\",\"LYD\":\"Libyan Dinar\",\"MAD\":\"Moroccan Dirham\",\"MDL\":\"Moldovan Leu\",\"MGA\":\"Malagasy Ariary\",\"MKD\":\"Macedonian Denar\",\"MMK\":\"Myanma Kyat\",\"MNT\":\"Mongolian Tugrik\",\"MOP\":\"Macanese Pataca\",\"MRO\":\"Mauritanian Ouguiya\",\"MTL\":\"Maltese Lira\",\"MUR\":\"Mauritian Rupee\",\"MVR\":\"Maldivian Rufiyaa\",\"MWK\":\"Malawian Kwacha\",\"MXN\":\"Mexican Peso\",\"MYR\":\"Malaysian Ringgit\",\"MZN\":\"Mozambican Metical\",\"NAD\":\"Namibian Dollar\",\"NGN\":\"Nigerian Naira\",\"NIO\":\"Nicaraguan Córdoba\",\"NOK\":\"Norwegian Krone\",\"NPR\":\"Nepalese Rupee\",\"NZD\":\"New Zealand Dollar\",\"OMR\":\"Omani Rial\",\"PAB\":\"Panamanian Balboa\",\"PEN\":\"Peruvian Nuevo Sol\",\"PGK\":\"Papua New Guinean Kina\",\"PHP\":\"Philippine Peso\",\"PKR\":\"Pakistani Rupee\",\"PLN\":\"Polish Zloty\",\"PYG\":\"Paraguayan Guarani\",\"QAR\":\"Qatari Rial\",\"RON\":\"Romanian Leu\",\"RSD\":\"Serbian Dinar\",\"RUB\":\"Russian Ruble\",\"RWF\":\"Rwandan Franc\",\"SAR\":\"Saudi Riyal\",\"SBD\":\"Solomon Islands Dollar\",\"SCR\":\"Seychellois Rupee\",\"SDG\":\"Sudanese Pound\",\"SEK\":\"Swedish Krona\",\"SGD\":\"Singapore Dollar\",\"SHP\":\"Saint Helena Pound\",\"SLL\":\"Sierra Leonean Leone\",\"SOS\":\"Somali Shilling\",\"SRD\":\"Surinamese Dollar\",\"STD\":\"São Tomé and Príncipe Dobra\",\"SVC\":\"Salvadoran Colón\",\"SYP\":\"Syrian Pound\",\"SZL\":\"Swazi Lilangeni\",\"THB\":\"Thai Baht\",\"TJS\":\"Tajikistani Somoni\",\"TMT\":\"Turkmenistani Manat\",\"TND\":\"Tunisian Dinar\",\"TOP\":\"Tongan Pa?anga\",\"TRY\":\"Turkish Lira\",\"TTD\":\"Trinidad and Tobago Dollar\",\"TWD\":\"New Taiwan Dollar\",\"TZS\":\"Tanzanian Shilling\",\"UAH\":\"Ukrainian Hryvnia\",\"UGX\":\"Ugandan Shilling\",\"USD\":\"United States Dollar\",\"UYU\":\"Uruguayan Peso\",\"UZS\":\"Uzbekistan Som\",\"VEF\":\"Venezuelan Bolívar Fuerte\",\"VND\":\"Vietnamese Dong\",\"VUV\":\"Vanuatu Vatu\",\"WST\":\"Samoan Tala\",\"XAF\":\"CFA Franc BEAC\",\"XAG\":\"Silver Ounce\",\"XAU\":\"Gold Ounce\",\"XCD\":\"East Caribbean Dollar\",\"XDR\":\"Special Drawing Rights\",\"XOF\":\"CFA Franc BCEAO\",\"XPD\":\"Palladium Ounce\",\"XPF\":\"CFP Franc\",\"XPT\":\"Platinum Ounce\",\"YER\":\"Yemeni Rial\",\"ZAR\":\"South African Rand\",\"ZMK\":\"Zambian Kwacha (pre-2013)\",\"ZMW\":\"Zambian Kwacha\",\"ZWL\":\"Zimbabwean Dollar\"}";
		JSONObject jsonObject = new JSONObject(jsonString);
		 if(currencyCode!=null && !jsonObject.has(currencyCode)){
			 errors.reject("from","Invalid FROM Currency code");
		 }
	 }
	 
	 private void validateTradeType(TradeInfo tradeInfo,Errors errors){
		 if(tradeInfo.getTradeType()!=null &&
				 ! tradeInfo.getTradeType().equals("ALL") && 
				 ! tradeInfo.getTradeType().equals("SPOT") && 
				 ! tradeInfo.getTradeType().equals("FORWARD") && 
				 ! tradeInfo.getTradeType().equals("OPTION")){
			 errors.reject("tradeType","Invalid Trade Type value. Accepted values are ALL , SPOT, FORWARD or OPTION");
		 }
		 
		
		 
	 }
}
