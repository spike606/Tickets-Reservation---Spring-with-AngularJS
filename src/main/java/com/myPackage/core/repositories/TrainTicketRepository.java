package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.TrainTicket;

public interface TrainTicketRepository {
    public TrainTicket createTrainTicket(TrainTicket data);
    public List<TrainTicket> findAllTrainTickets();
    public TrainTicket findTrainTicket(Long id);
    public TrainTicket deleteTrainTicket(Long id);
    public TrainTicket updateTrainTicket(Long id, TrainTicket data);
}
