package com.myPackage.core.services;

import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.util.PlaneTicketOrderList;

public interface PlaneTicketOrderService {

	public PlaneTicketOrder createPlaneTicketOrder(Long accountId, PlaneTicketOrder data);// TODO:
																			// OK?
	public PlaneTicketOrder createPlaneTicketOrder(PlaneTicketOrder data);// TODO:

	public PlaneTicketOrderList findAllPlaneTicketOrders();

	public PlaneTicketOrder findPlaneTicketOrder(Long id);

	public PlaneTicketOrder deletePlaneTicketOrder(Long id);

	public PlaneTicketOrder updatePlaneTicketOrder(Long id, PlaneTicketOrder data);

}
