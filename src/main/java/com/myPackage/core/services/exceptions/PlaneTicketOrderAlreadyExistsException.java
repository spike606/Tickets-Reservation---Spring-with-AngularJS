package com.myPackage.core.services.exceptions;

public class PlaneTicketOrderAlreadyExistsException extends RuntimeException{
    public PlaneTicketOrderAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public PlaneTicketOrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaneTicketOrderAlreadyExistsException(String message) {
        super(message);
    }

    public PlaneTicketOrderAlreadyExistsException() {
    }

}
