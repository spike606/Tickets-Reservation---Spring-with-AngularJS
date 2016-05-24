package com.myPackage.core.services.exceptions;

public class TrainTicketNotFoundException extends RuntimeException{
    public TrainTicketNotFoundException(Throwable cause) {
        super(cause);
    }

    public TrainTicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainTicketNotFoundException(String message) {
        super(message);
    }

    public TrainTicketNotFoundException() {
    }
}
