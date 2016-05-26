package com.myPackage.core.services;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;

public interface TrainTicketOrderService {
	
//    public TrainTicketOrder createTrainTicketOrder(Long accountId, TrainTicketOrder data);
    public TrainTicketOrder createTrainTicketOrder(TrainTicketOrder data);
    public TrainTicketOrderList findAllTrainTicketOrders();
	public TrainTicketOrder findTrainTicketOrder(Long id);

	public TrainTicketOrderList findTrainOrdersByAccount(String login);
    public TrainTicketOrder deleteTrainTicketOrder(Long id);
	
}
