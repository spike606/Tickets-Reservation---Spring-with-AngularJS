package com.myPackage.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.myPackage.core.entities.TrainTicketOrder;

public class TrainTicketOrderList {
	 private List<TrainTicketOrder> trainTicketOrderList = new ArrayList<TrainTicketOrder>();

	public TrainTicketOrderList() {
	}
	 
	public TrainTicketOrderList(List<TrainTicketOrder> trainTicketOrderList) {
		this.trainTicketOrderList = trainTicketOrderList;
	}

	public List<TrainTicketOrder> getTrainTicketOrders() {
		return trainTicketOrderList;
	}

	public void setTrainTicketOrders(List<TrainTicketOrder> trainTicketOrderList) {
		this.trainTicketOrderList = trainTicketOrderList;
	}
}
