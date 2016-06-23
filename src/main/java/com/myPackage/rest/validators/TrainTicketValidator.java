package com.myPackage.rest.validators;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.myPackage.rest.exceptions.ValidationException;
import com.myPackage.rest.resources.TrainTicketResource;



public class TrainTicketValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String TRANSIT_NUMBER_PATTERN = "[a-zA-Z0-9]{1,9}";
	private static final String TRANSIT_NAME_PATTERN = "[a-zA-Z\\s]{1,25}";
	private static final String CITY_PATTERN = "[a-zA-Z\\s]{1,25}";
	private static final String DATE_PATTERN = "[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])";// YYYY/MM/DD
	private static final String HOUR_PATTER_24 = "([01]?[0-9]|2[0-3]):[0-5][0-9]";// 24
																					// hour
																					// pattern
	private static final String PRICE_PATTERN = "[1-9]{1}[0-9]{0,10}";// PRICE

	@Override
	public boolean supports(Class clazz) {
		return (TrainTicketResource.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//annotation based validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		javax.validation.Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validationErrors = validator.validate(target);

		//custom validation
		TrainTicketResource trainTicketResource = (TrainTicketResource) target;

		doesRequiredFieldCorrect(TRANSIT_NUMBER_PATTERN, trainTicketResource.getTransitNumber());
		doesRequiredFieldCorrect(TRANSIT_NAME_PATTERN, trainTicketResource.getTransitName());
		doesRequiredFieldCorrect(CITY_PATTERN, trainTicketResource.getTransitFrom());
		doesRequiredFieldCorrect(CITY_PATTERN, trainTicketResource.getTransitTo());
		doesRequiredFieldCorrect(DATE_PATTERN, trainTicketResource.getTransitDateStart());
		doesRequiredFieldCorrect(HOUR_PATTER_24, trainTicketResource.getTransitHourStart());
		doesRequiredFieldCorrect(DATE_PATTERN, trainTicketResource.getTransitDateStop());
		doesRequiredFieldCorrect(HOUR_PATTER_24, trainTicketResource.getTransitHourStop());
		doesRequiredFieldCorrect(PRICE_PATTERN, trainTicketResource.getTransitPrice());

	}


	private void doesRequiredFieldCorrect(String regexPattern, String valueToCheck){
		if (invalidFieldRequired(regexPattern, valueToCheck)) {
			throw new ValidationException();
		}
	}
	private void doesNotRequiredFieldCorrect(String regexPattern, String valueToCheck){
		if (invalidFieldNotRequired(regexPattern, valueToCheck)) {
			throw new ValidationException();
		}
	}

	private boolean invalidFieldRequired(String regexPattern, String valueToCheck) {
		pattern = Pattern.compile(regexPattern);
		if(valueToCheck == null || valueToCheck.isEmpty())
			return true;
			
		matcher = pattern.matcher(valueToCheck);
			if (!matcher.matches()) {
				return true;
			}
		
		return false;

	}
	private boolean invalidFieldNotRequired(String regexPattern, String valueToCheck) {
		pattern = Pattern.compile(regexPattern);
		if(valueToCheck != null || !valueToCheck.isEmpty()){
			matcher = pattern.matcher(valueToCheck);
			if (!matcher.matches()) {
				return true;
			}			
		}

		return false;

	}
}
