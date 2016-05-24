package com.myPackage.core.services.exceptions;

public class PlaneTicketAlreadyExistsException extends RuntimeException{
    public PlaneTicketAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public PlaneTicketAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaneTicketAlreadyExistsException(String message) {
        super(message);
    }

    public PlaneTicketAlreadyExistsException() {
    }
}
