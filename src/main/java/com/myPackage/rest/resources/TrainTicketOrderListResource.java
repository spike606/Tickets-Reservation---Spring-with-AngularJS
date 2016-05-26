package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class TrainTicketOrderListResource extends ResourceSupport{
	 private List<TrainTicketOrderResource> trainTicketOrderResource = new ArrayList<TrainTicketOrderResource>();

	public List<TrainTicketOrderResource> getTrainTicketOrders() {
		return trainTicketOrderResource;
	}

	public void setTrainTicketOrders(List<TrainTicketOrderResource> trainTicketOrderResource) {
		this.trainTicketOrderResource = trainTicketOrderResource;
	}

}
