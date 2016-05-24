package com.myPackage.rest.resources.asm;

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
        List<TrainTicketOrderResource> resources = new TrainTicketOrderResourceAsm().toResources(trainTicketOrderList.getTrainTicketOrders());
        TrainTicketOrderListResource listResource = new TrainTicketOrderListResource();
        listResource.setTrainTicketOrderResource(resources);
//		Link link = linkTo(PlaneTicketController.class).slash.framedump(planeTicketList.getPlaneTickets()).withSelfRel();
//TODO: LINK
        return listResource;
	}
}
