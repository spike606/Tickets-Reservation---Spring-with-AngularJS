package com.myPackage.core.services;

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.services.util.TrainTicketList;

public interface TrainTicketService {
    public TrainTicket createTrainTicket(TrainTicket data);
    public TrainTicketList findAllTrainTickets();
    public TrainTicket findTrainTicket(Long id);
    public TrainTicket deleteTrainTicket(Long id);
    public TrainTicket updateTrainTicket(Long id, TrainTicket data);

}
