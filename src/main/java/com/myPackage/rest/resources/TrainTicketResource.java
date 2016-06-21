package com.myPackage.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.myPackage.core.entities.TrainTicket;

public class TrainTicketResource extends ResourceSupport{
	private String transitNumber;
	private String transitName;
	private String transitFrom;
	private String transitTo;
	private String transitDateStart;
	private String transitHourStart;
	private String transitDateStop;
	private String transitHourStop;
	private String transitPrice;
	private Long rid;
	
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getTransitNumber() {
		return transitNumber;
	}

	public void setTransitNumber(String transitNumber) {
		this.transitNumber = transitNumber;
	}

	public String getTransitName() {
		return transitName;
	}

	public void setTransitName(String transitName) {
		this.transitName = transitName;
	}

	public String getTransitFrom() {
		return transitFrom;
	}

	public void setTransitFrom(String transitFrom) {
		this.transitFrom = transitFrom;
	}

	public String getTransitTo() {
		return transitTo;
	}

	public void setTransitTo(String transitTo) {
		this.transitTo = transitTo;
	}

	public String getTransitDateStart() {
		return transitDateStart;
	}

	public void setTransitDateStart(String transitDateStart) {
		this.transitDateStart = transitDateStart;
	}

	public String getTransitHourStart() {
		return transitHourStart;
	}

	public void setTransitHourStart(String transitHourStart) {
		this.transitHourStart = transitHourStart;
	}

	public String getTransitDateStop() {
		return transitDateStop;
	}

	public void setTransitDateStop(String transitDateStop) {
		this.transitDateStop = transitDateStop;
	}

	public String getTransitHourStop() {
		return transitHourStop;
	}

	public void setTransitHourStop(String transitHourStop) {
		this.transitHourStop = transitHourStop;
	}

	public String getTransitPrice() {
		return transitPrice;
	}

	public void setTransitPrice(String transitPrice) {
		this.transitPrice = transitPrice;
	}

	public TrainTicket toTrainTicket() {
		TrainTicket trainTicket = new TrainTicket();
		trainTicket.setTransitNumber(transitNumber);
		trainTicket.setTransitName(transitName);
		trainTicket.setTransitFrom(transitFrom);
		trainTicket.setTransitTo(transitTo);
		trainTicket.setTransitDateStart(transitDateStart);
		trainTicket.setTransitHourStart(transitHourStart);
		trainTicket.setTransitDateStop(transitDateStop);
		trainTicket.setTransitHourStop(transitHourStop);
		trainTicket.setTransitPrice(transitPrice);

		return trainTicket;

	}
}
