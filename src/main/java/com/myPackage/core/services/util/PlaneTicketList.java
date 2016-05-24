package com.myPackage.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.myPackage.core.entities.PlaneTicket;

public class PlaneTicketList {
	private List<PlaneTicket> planeTickets = new ArrayList<PlaneTicket>();

	public List<PlaneTicket> getPlaneTickets() {
		return planeTickets;
	}

	public void setPlaneTickets(List<PlaneTicket> planeTickets) {
		this.planeTickets = planeTickets;
	}
	
}
