package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.PlaneTicketOrder;

public interface PlaneTicketOrderRepository {
	
	public PlaneTicketOrder createPlaneTicketOrder(PlaneTicketOrder data);
	public List<PlaneTicketOrder> findAllPlaneTicketOrders();
	public PlaneTicketOrder findPlaneTicketOrder(Long id);
	public PlaneTicketOrder deletePlaneTicketOrder(Long id);
	public List<PlaneTicketOrder> findAllPlaneTicketOrdersForAccount(Long accountId);

//  public PlaneTicketOrder updatePlaneTicketOrder(Long id, PlaneTicketOrder data);


}
