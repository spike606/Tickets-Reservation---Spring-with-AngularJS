package com.myPackage.core.repositories;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;

public interface AccountRepository {
    public Account findAccount(Long id);
    public PlaneTicketOrderList findAllPlaneTicketOrdersForAccount(Long accountId);
    public TrainTicketOrderList findAllTrainTicketOrdersForAccount(Long accountId);
    public Account createAccount(Account data);
    public PlaneTicketOrder createPlaneTicketOrder(Long planeTicketOrderId, PlaneTicketOrder data);
    public TrainTicketOrder createTrainTicketOrder(Long trainTicketOrderId, TrainTicketOrder data);
}
