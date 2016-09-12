/**
 * 
 */

/**
 * @author Balarkan Babith
 *
 */
package com.codetest.controller;



import java.util.Calendar;
import java.util.Currency;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codetest.generated.Trade;
import com.codetest.utils.ValidationErrorBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/task")
public class CodetestRestController{
	
	private static final Logger LOGGER = Logger.getLogger(CodetestRestController.class.getName());
	
	private ObjectMapper jacksonObjectMapper;
	
	private Validator jsonValidator;
	
	
	public Validator getJsonValidator() {
		return jsonValidator;
	}
	
	@Autowired
	public void setJsonValidator(Validator jsonValidator) {
		this.jsonValidator = jsonValidator;
	}

	public ObjectMapper getJacksonObjectMapper() {
		return jacksonObjectMapper;
	}

	@Autowired
	public void setJacksonObjectMapper(ObjectMapper jacksonObjectMapper) {
		this.jacksonObjectMapper = jacksonObjectMapper;
	}
	
 @RequestMapping(value = "/{clientName}",method = RequestMethod.POST,
		 produces="application/json", 
		 consumes="application/json")
 public @ResponseBody String tradingService(@PathVariable("clientName") String client,
		 @Valid @RequestBody  Trade tradeDetails,BindingResult errors ) throws JsonProcessingException {
	 
	  jsonValidator.validate(tradeDetails, errors);
	  if (errors.hasErrors()) {
	      return jacksonObjectMapper.writeValueAsString(ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors)));
		}
	  return jacksonObjectMapper.writeValueAsString(ResponseEntity.ok(tradeDetails));
 }
 

 
}
