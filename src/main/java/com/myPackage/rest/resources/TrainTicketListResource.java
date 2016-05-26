package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class TrainTicketListResource extends ResourceSupport{
	 private List<TrainTicketResource> trainTickets = new ArrayList<TrainTicketResource>();

	public List<TrainTicketResource> getTrainTickets() {
		return trainTickets;
	}

	public void setTrainTickets(List<TrainTicketResource> trainTickets) {
		this.trainTickets = trainTickets;
	}

}
