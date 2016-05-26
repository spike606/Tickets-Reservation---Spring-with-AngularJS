package com.myPackage.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.myPackage.rest.mvc.PlaneTicketController;

import com.myPackage.core.services.util.PlaneTicketList;
import com.myPackage.rest.resources.PlaneTicketListResource;
import com.myPackage.rest.resources.PlaneTicketResource;

public class PlaneTicketListResourceAsm extends ResourceAssemblerSupport<PlaneTicketList, PlaneTicketListResource> {

	public PlaneTicketListResourceAsm() {
		super(PlaneTicketController.class, PlaneTicketListResource.class);
	}

	@Override
	public PlaneTicketListResource toResource(PlaneTicketList planeTicketList) {
		List<PlaneTicketResource> resources = new PlaneTicketResourceAsm().toResources(planeTicketList.getPlaneTickets());
		PlaneTicketListResource listResource = new PlaneTicketListResource();
		listResource.setPlaneTickets(resources);
		listResource.add(linkTo(methodOn(PlaneTicketController.class).findAllPlaneTickets()).withSelfRel());

		return listResource;
	}

}
