package com.myPackage.core.services.exceptions;

public class PlaneTicketNotFoundException extends RuntimeException{
	public PlaneTicketNotFoundException(Throwable cause) {
		super(cause);
	}

	public PlaneTicketNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlaneTicketNotFoundException(String message) {
		super(message);
	}

	public PlaneTicketNotFoundException() {
	}}
