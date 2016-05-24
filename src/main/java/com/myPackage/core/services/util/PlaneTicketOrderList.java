package com.myPackage.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.myPackage.core.entities.PlaneTicketOrder;

public class PlaneTicketOrderList {
	 private List<PlaneTicketOrder> planeTicketOrders = new ArrayList<PlaneTicketOrder>();

	public List<PlaneTicketOrder> getPlaneTicketOrders() {
		return planeTicketOrders;
	}

	public void setPlaneTicketOrders(List<PlaneTicketOrder> planeTicketOrders) {
		this.planeTicketOrders = planeTicketOrders;
	}
	 
}
