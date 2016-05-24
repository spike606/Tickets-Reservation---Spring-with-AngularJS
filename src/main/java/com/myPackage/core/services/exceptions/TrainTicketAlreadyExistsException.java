package com.myPackage.core.services.exceptions;

public class TrainTicketAlreadyExistsException extends RuntimeException{
    public TrainTicketAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TrainTicketAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainTicketAlreadyExistsException(String message) {
        super(message);
    }

    public TrainTicketAlreadyExistsException() {
    }
}
