package com.myPackage.core.services;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.util.TrainTicketOrderList;

public interface TrainTicketOrderService {
	
    public TrainTicketOrder createTrainTicketOrder(Long id);
    public TrainTicketOrderList findAllTrainTicketOrders();
    public TrainTicketOrder findTrainTicketOrder(Long id);
    public TrainTicketOrder deleteTrainTicketOrder(Long id);
    public TrainTicketOrder updateTrainTicketOrder(Long id, TrainTicketOrder data);
	
}
