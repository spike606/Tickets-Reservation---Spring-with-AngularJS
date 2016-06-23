package com.myPackage.rest.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ValidatorExceptionHandlerController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler({ValidationException.class})
	public void processCalidationExceptions(HttpServletRequest req, Exception ex) {

	}
}