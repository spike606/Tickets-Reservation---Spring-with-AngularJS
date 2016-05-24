
package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class PlaneTicketListResource extends ResourceSupport {
	 private List<PlaneTicketResource> planeTickets = new ArrayList<PlaneTicketResource>();

	public List<PlaneTicketResource> getPlaneTickets() {
		return planeTickets;
	}

	public void setPlaneTickets(List<PlaneTicketResource> planeTickets) {
		this.planeTickets = planeTickets;
	}
	 
}
