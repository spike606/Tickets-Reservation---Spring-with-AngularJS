package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class TrainTicketListResource extends ResourceSupport{
	 private List<TrainTicketResource> trainTicketResource = new ArrayList<TrainTicketResource>();

	public List<TrainTicketResource> getTrainTicketResource() {
		return trainTicketResource;
	}

	public void setTrainTicketResource(List<TrainTicketResource> trainTicketResource) {
		this.trainTicketResource = trainTicketResource;
	}

}
