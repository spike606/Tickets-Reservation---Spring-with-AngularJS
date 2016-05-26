package com.myPackage.core.services;

import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.util.PlaneTicketOrderList;

public interface PlaneTicketOrderService {

//	public PlaneTicketOrder createPlaneTicketOrder(Long accountId, PlaneTicketOrder data);
																			
	public PlaneTicketOrder createPlaneTicketOrder(PlaneTicketOrder data);

	public PlaneTicketOrderList findAllPlaneTicketOrders();
	
	public PlaneTicketOrder findPlaneTicketOrder(Long id);

	public PlaneTicketOrderList findPlaneTicketOrdersByAccount(String login);

	public PlaneTicketOrder deletePlaneTicketOrder(Long id);


}
