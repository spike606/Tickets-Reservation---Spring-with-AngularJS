package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.rest.mvc.PlaneTicketOrderController;
import com.myPackage.rest.resources.PlaneTicketOrderResource;

public class PlaneTicketOrderResourceAsm extends ResourceAssemblerSupport<PlaneTicketOrder, PlaneTicketOrderResource>{

	public PlaneTicketOrderResourceAsm() {
		super(PlaneTicketOrderController.class, PlaneTicketOrderResource.class);
	}

	@Override
	public PlaneTicketOrderResource toResource(PlaneTicketOrder planeTicketOrder) {
		PlaneTicketOrderResource res = new PlaneTicketOrderResource();
        res.setFirstname(planeTicketOrder.getFirstname());
		res.setSecondname(planeTicketOrder.getSecondname());
		res.setLastname(planeTicketOrder.getLastname());
		res.setEmail(planeTicketOrder.getEmail());
		res.setTelephone(planeTicketOrder.getTelephone());
		res.setCountry(planeTicketOrder.getCountry());
		res.setCity(planeTicketOrder.getCity());
		res.setState(planeTicketOrder.getState());
		res.setStreet(planeTicketOrder.getStreet());
		res.setPlaneTicketId(planeTicketOrder.getPlaneTicket().getId());
		res.setOwner(planeTicketOrder.getOwner());
		Link self = linkTo(PlaneTicketOrderController.class).slash(planeTicketOrder.getId()).withSelfRel();
		
		res.add(self);

		return res;
	}


}
