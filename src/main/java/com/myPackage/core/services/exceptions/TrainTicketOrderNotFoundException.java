package com.myPackage.core.services.exceptions;

public class TrainTicketOrderNotFoundException extends RuntimeException{
    public TrainTicketOrderNotFoundException(Throwable cause) {
        super(cause);
    }

    public TrainTicketOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainTicketOrderNotFoundException(String message) {
        super(message);
    }

    public TrainTicketOrderNotFoundException() {
    }
}
