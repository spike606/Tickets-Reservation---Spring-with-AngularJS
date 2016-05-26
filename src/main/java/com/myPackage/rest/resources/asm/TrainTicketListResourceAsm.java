package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.services.util.TrainTicketList;
import com.myPackage.rest.mvc.TrainTicketController;
import com.myPackage.rest.resources.TrainTicketListResource;
import com.myPackage.rest.resources.TrainTicketResource;

public class TrainTicketListResourceAsm extends ResourceAssemblerSupport<TrainTicketList, TrainTicketListResource>{
	
	public TrainTicketListResourceAsm() {
		super(TrainTicketController.class, TrainTicketListResource.class);
	}

	@Override
	public TrainTicketListResource toResource(TrainTicketList trainTicketList) {
        List<TrainTicketResource> resources = new TrainTicketResourceAsm().toResources(trainTicketList.getTrainTickets());
        TrainTicketListResource listResource = new TrainTicketListResource();
        listResource.setTrainTickets(resources);
		listResource.add(linkTo(methodOn(TrainTicketController.class).findAllTrainTickets()).withSelfRel());

        return listResource;
	}
}
