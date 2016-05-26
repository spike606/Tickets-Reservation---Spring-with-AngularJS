package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.rest.mvc.PlaneTicketOrderController;
import com.myPackage.rest.resources.PlaneTicketOrderListResource;
import com.myPackage.rest.resources.PlaneTicketOrderResource;

public class PlaneTicketOrderListResourceAsm extends ResourceAssemblerSupport<PlaneTicketOrderList, PlaneTicketOrderListResource>{
	public PlaneTicketOrderListResourceAsm() {
		super(PlaneTicketOrderController.class, PlaneTicketOrderListResource.class);
	}

	@Override
	public PlaneTicketOrderListResource toResource(PlaneTicketOrderList planeTicketOrderList) {
        PlaneTicketOrderListResource listResource = new PlaneTicketOrderListResource();

	        List<PlaneTicketOrderResource> resources = new PlaneTicketOrderResourceAsm().toResources(planeTicketOrderList.getPlaneTicketOrders());
	        listResource.setPlaneTicketOrders(resources);
	        listResource.add(linkTo(methodOn(PlaneTicketOrderController.class).findAllPlaneTicketOrders()).withSelfRel());
		

        
        return listResource;
	}
}
