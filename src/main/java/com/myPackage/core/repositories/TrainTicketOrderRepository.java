package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.TrainTicketOrder;

public interface TrainTicketOrderRepository {
    public TrainTicketOrder createTrainTicketOrder(TrainTicketOrder data);
    public List<TrainTicketOrder> findAllTrainTicketOrders();
    public TrainTicketOrder findTrainTicketOrder(Long id);
    public TrainTicketOrder deleteTrainTicketOrder(Long id);
	public List<TrainTicketOrder> findAllTrainTicketOrdersForAccount(Long accountId);

//    public TrainTicketOrder updateTrainTicketOrder(Long id, TrainTicketOrder data);
}
