package com.myPackage.core.services.exceptions;

public class TrainTicketOrderAlreadyExistsException extends RuntimeException{

    public TrainTicketOrderAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TrainTicketOrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainTicketOrderAlreadyExistsException(String message) {
        super(message);
    }

    public TrainTicketOrderAlreadyExistsException() {
    }	
	
}
