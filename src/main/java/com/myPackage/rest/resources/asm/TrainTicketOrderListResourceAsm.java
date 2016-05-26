package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.services.util.TrainTicketOrderList;
import com.myPackage.rest.mvc.TrainTicketOrderController;
import com.myPackage.rest.resources.TrainTicketOrderListResource;
import com.myPackage.rest.resources.TrainTicketOrderResource;

public class TrainTicketOrderListResourceAsm extends ResourceAssemblerSupport<TrainTicketOrderList, TrainTicketOrderListResource>{
	public TrainTicketOrderListResourceAsm() {
		super(TrainTicketOrderController.class, TrainTicketOrderListResource.class);
	}

	@Override
	public TrainTicketOrderListResource toResource(TrainTicketOrderList trainTicketOrderList) {
        TrainTicketOrderListResource listResource = new TrainTicketOrderListResource();

	        List<TrainTicketOrderResource> resources = new TrainTicketOrderResourceAsm().toResources(trainTicketOrderList.getTrainTicketOrders());
	        listResource.setTrainTicketOrders(resources);
	        listResource.add(linkTo(methodOn(TrainTicketOrderController.class).findAllTrainTicketOrders()).withSelfRel());
        return listResource;
	}
}
