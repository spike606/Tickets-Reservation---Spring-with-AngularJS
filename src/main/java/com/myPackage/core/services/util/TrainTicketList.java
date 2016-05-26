package com.myPackage.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.myPackage.core.entities.TrainTicket;

public class TrainTicketList {
	private List<TrainTicket> trainTicketList = new ArrayList<TrainTicket>();

	public TrainTicketList() {
	}
	public TrainTicketList(List<TrainTicket> trainTicketList) {
		this.trainTicketList = trainTicketList;
	}

	public List<TrainTicket> getTrainTickets() {
		return trainTicketList;
	}

	public void setTrainTickets(List<TrainTicket> trainTicketList) {
		this.trainTicketList = trainTicketList;
	}
	
	

}
