package com.myPackage.rest.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.myPackage.rest.exceptions.ValidationException;
import com.myPackage.rest.resources.PlaneTicketOrderResource;


public class PlaneTicketOrderValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String NAME_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]{1,25}";
	private static final String COUNTRY_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]{1,25}";
	private static final String STATE_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9\\s]{1,25}";
	private static final String CITY_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]{1,25}";
	private static final String STREET_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9\\s]{1,25}";
	private static final String PHONE_PATTERN = "\\+[0-9]{1,4}\\s[0-9]{3}-[0-9]{3}-[0-9]{3}";
    private final static String EMAIL_PATTERN = "(?!\\.)(?!.*\\.{2})[a-zA-Z0-9._\\%!#\\$&'\\*+-/=\\?\\[\\]\\^`\\{\\|\\}~]+(?<!\\.)@(?!-)[a-zA-Z0-9-]+(?<!-).[A-Za-z]{2,4}";//negative lookahead for ..

	@Override
	public boolean supports(Class clazz) {
		return (PlaneTicketOrderResource.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//annotation based validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		javax.validation.Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validationErrors = validator.validate(target);

		//custom validation
		PlaneTicketOrderResource planeTicketOrderResource = (PlaneTicketOrderResource) target;

		doesRequiredFieldCorrect(NAME_PATTERN, planeTicketOrderResource.getFirstname());
		doesRequiredFieldCorrect(NAME_PATTERN, planeTicketOrderResource.getLastname());
		doesNotRequiredFieldCorrect(NAME_PATTERN, planeTicketOrderResource.getSecondname());
		doesRequiredFieldCorrect(EMAIL_PATTERN, planeTicketOrderResource.getEmail());
		doesRequiredFieldCorrect(PHONE_PATTERN, planeTicketOrderResource.getTelephone());
		doesRequiredFieldCorrect(COUNTRY_PATTERN, planeTicketOrderResource.getCountry());
		doesRequiredFieldCorrect(STATE_PATTERN, planeTicketOrderResource.getState());
		doesRequiredFieldCorrect(CITY_PATTERN, planeTicketOrderResource.getCity());
		doesRequiredFieldCorrect(STREET_PATTERN, planeTicketOrderResource.getStreet());

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
