package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.rest.mvc.PlaneTicketController;
import com.myPackage.rest.resources.PlaneTicketResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class PlaneTicketResourceAsm extends ResourceAssemblerSupport<PlaneTicket, PlaneTicketResource>{

	public PlaneTicketResourceAsm() {
		super(PlaneTicketController.class, PlaneTicketResource.class);
	}

	@Override
	public PlaneTicketResource toResource(PlaneTicket planeTicket) {
		PlaneTicketResource res = new PlaneTicketResource();
       
        res.setFlightNumber(planeTicket.getFlightNumber());
        res.setFlightFrom(planeTicket.getFlightFrom());
        res.setFlightTo(planeTicket.getFlightTo());
        res.setFlightDateStart(planeTicket.getFlightDateStart());
        res.setFlightHourStart(planeTicket.getFlightHourStart());
        res.setFlightDateStop(planeTicket.getFlightDateStop());
        res.setFlightHourStop(planeTicket.getFlightHourStop());
        res.setFlightPrice(planeTicket.getFlightPrice());
        res.setRid(planeTicket.getId());
		res.add(linkTo(methodOn(PlaneTicketController.class).getPlaneTicket(planeTicket.getId())).withSelfRel());
		return res;
	}

}
