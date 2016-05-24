package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.rest.mvc.TrainTicketOrderController;
import com.myPackage.rest.resources.TrainTicketOrderResource;

public class TrainTicketOrderResourceAsm extends ResourceAssemblerSupport<TrainTicketOrder, TrainTicketOrderResource>{

	public TrainTicketOrderResourceAsm() {
		super(TrainTicketOrderController.class, TrainTicketOrderResource.class);
	}

	@Override
	public TrainTicketOrderResource toResource(TrainTicketOrder trainTicketOrder) {
		TrainTicketOrderResource res = new TrainTicketOrderResource();
	       
		res.setFirstname(trainTicketOrder.getFirstname());
		res.setSecondname(trainTicketOrder.getSecondname());
		res.setLastname(trainTicketOrder.getLastname());
		res.setEmail(trainTicketOrder.getEmail());
		res.setTelephone(trainTicketOrder.getTelephone());
		res.setCountry(trainTicketOrder.getCountry());
		res.setCity(trainTicketOrder.getCity());
		res.setState(trainTicketOrder.getState());
		res.setStreet(trainTicketOrder.getStreet());
		res.setTrainTicket(trainTicketOrder.getTrainTicket());
		res.setOwner(trainTicketOrder.getOwner());

		Link link = linkTo(TrainTicketOrderController.class).slash(trainTicketOrder.getId()).withSelfRel();
		res.add(link);
		return res;
	}

}
