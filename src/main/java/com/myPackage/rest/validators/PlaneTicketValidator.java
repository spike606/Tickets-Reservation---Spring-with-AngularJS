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
import com.myPackage.rest.resources.PlaneTicketResource;


public class PlaneTicketValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String FLIGHT_NUMBER_PATTERN = "[a-zA-Z0-9]{1,9}";
	private static final String CITY_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]{1,25}";
	private static final String DATE_PATTERN = "[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])";// YYYY-MM-DD
	private static final String HOUR_PATTER_24 = "([01]?[0-9]|2[0-3]):[0-5][0-9]";// 24
																					// hour
																					// pattern
	private static final String PRICE_PATTERN = "[1-9]{1}[0-9]{1,10}";// PRICE


	@Override
	public boolean supports(Class clazz) {
		return (PlaneTicketResource.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//annotation based validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		javax.validation.Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validationErrors = validator.validate(target);

		//custom validation
		PlaneTicketResource planeTicketResource = (PlaneTicketResource) target;

		doesRequiredFieldCorrect(FLIGHT_NUMBER_PATTERN, planeTicketResource.getFlightNumber());
		doesRequiredFieldCorrect(CITY_PATTERN, planeTicketResource.getFlightFrom());
		doesRequiredFieldCorrect(CITY_PATTERN, planeTicketResource.getFlightTo());
		doesRequiredFieldCorrect(DATE_PATTERN, planeTicketResource.getFlightDateStart());
		doesRequiredFieldCorrect(HOUR_PATTER_24, planeTicketResource.getFlightHourStart());
		doesRequiredFieldCorrect(DATE_PATTERN, planeTicketResource.getFlightDateStop());
		doesRequiredFieldCorrect(HOUR_PATTER_24, planeTicketResource.getFlightHourStop());
		doesRequiredFieldCorrect(PRICE_PATTERN, planeTicketResource.getFlightPrice());

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
		if(valueToCheck == null)
			return false;
		matcher = pattern.matcher(valueToCheck);
		if (!matcher.matches()) {
			return true;
		}	
		return false;

	}


}
