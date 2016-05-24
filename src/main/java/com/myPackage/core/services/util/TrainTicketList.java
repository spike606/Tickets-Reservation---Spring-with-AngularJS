package com.myPackage.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.myPackage.core.entities.TrainTicket;

public class TrainTicketList {
	private List<TrainTicket> trainTicketList = new ArrayList<TrainTicket>();

	public List<TrainTicket> getTrainTicketList() {
		return trainTicketList;
	}

	public void setTrainTicketList(List<TrainTicket> trainTicketList) {
		this.trainTicketList = trainTicketList;
	}
	
	

}
