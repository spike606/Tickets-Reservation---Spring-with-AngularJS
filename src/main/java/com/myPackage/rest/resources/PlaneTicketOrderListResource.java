package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class PlaneTicketOrderListResource extends ResourceSupport{
	 private List<PlaneTicketOrderResource> planeTicketOrders = new ArrayList<PlaneTicketOrderResource>();

	public List<PlaneTicketOrderResource> getPlaneTicketOrders() {
		return planeTicketOrders;
	}

	public void setPlaneTicketOrders(List<PlaneTicketOrderResource> planeTicketOrders) {
		this.planeTicketOrders = planeTicketOrders;
	}
	 


}
