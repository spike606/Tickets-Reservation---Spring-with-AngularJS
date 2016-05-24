package com.myPackage.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.services.util.TrainTicketList;
import com.myPackage.rest.mvc.TrainTicketController;
import com.myPackage.rest.resources.PlaneTicketListResource;
import com.myPackage.rest.resources.TrainTicketListResource;
import com.myPackage.rest.resources.TrainTicketResource;

public class TrainTicketListResourceAsm extends ResourceAssemblerSupport<TrainTicketList, TrainTicketListResource>{
	
	public TrainTicketListResourceAsm(Class<?> controllerClass, Class<PlaneTicketListResource> resourceType) {
		super(TrainTicketController.class, TrainTicketListResource.class);
	}

	@Override
	public TrainTicketListResource toResource(TrainTicketList trainTicketList) {
        List<TrainTicketResource> resources = new TrainTicketResourceAsm().toResources(trainTicketList.getTrainTicketList());
        TrainTicketListResource listResource = new TrainTicketListResource();
        listResource.setTrainTicketResource(resources);
//		Link link = linkTo(PlaneTicketController.class).slash.framedump(planeTicketList.getPlaneTickets()).withSelfRel();
//TODO: LINK
        return listResource;
	}
}
