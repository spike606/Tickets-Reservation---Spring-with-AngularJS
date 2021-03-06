package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.rest.mvc.TrainTicketController;
import com.myPackage.rest.resources.TrainTicketResource;

public class TrainTicketResourceAsm  extends ResourceAssemblerSupport<TrainTicket, TrainTicketResource>{
	public TrainTicketResourceAsm() {
		super(TrainTicketController.class, TrainTicketResource.class);
	}

	@Override
	public TrainTicketResource toResource(TrainTicket trainTicket) {
		TrainTicketResource res = new TrainTicketResource();
       
		res.setTransitNumber(trainTicket.getTransitNumber());
		res.setTransitName(trainTicket.getTransitName());
		res.setTransitFrom(trainTicket.getTransitFrom());
		res.setTransitTo(trainTicket.getTransitTo());
		res.setTransitDateStart(trainTicket.getTransitDateStart());
		res.setTransitHourStart(trainTicket.getTransitHourStart());
		res.setTransitDateStop(trainTicket.getTransitDateStop());
		res.setTransitHourStop(trainTicket.getTransitHourStop());
		res.setTransitPrice(trainTicket.getTransitPrice());
        res.setRid(trainTicket.getId());
		res.add(linkTo(methodOn(TrainTicketController.class).getTrainTicket(trainTicket.getId())).withSelfRel());

		return res;
	}
}
