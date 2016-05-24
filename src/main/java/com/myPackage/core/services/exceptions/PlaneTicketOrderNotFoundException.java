package com.myPackage.core.services.exceptions;

public class PlaneTicketOrderNotFoundException extends RuntimeException{
    public PlaneTicketOrderNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlaneTicketOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaneTicketOrderNotFoundException(String message) {
        super(message);
    }

    public PlaneTicketOrderNotFoundException() {
    }
}
