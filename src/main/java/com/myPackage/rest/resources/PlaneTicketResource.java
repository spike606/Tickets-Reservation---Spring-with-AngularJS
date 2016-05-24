package com.myPackage.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.myPackage.core.entities.PlaneTicket;

public class PlaneTicketResource extends ResourceSupport{
	private String flightNumber;
	private String flightFrom;
	private String flightTo;
	private String flightDateStart;
	private String flightHourStart;
	private String flightDateStop;
	private String flightHourStop;
	private String flightPrice;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightFrom() {
		return flightFrom;
	}
	public void setFlightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}
	public String getFlightTo() {
		return flightTo;
	}
	public void setFlightTo(String flightTo) {
		this.flightTo = flightTo;
	}
	public String getFlightDateStart() {
		return flightDateStart;
	}
	public void setFlightDateStart(String flightDateStart) {
		this.flightDateStart = flightDateStart;
	}
	public String getFlightHourStart() {
		return flightHourStart;
	}
	public void setFlightHourStart(String flightHourStart) {
		this.flightHourStart = flightHourStart;
	}
	public String getFlightDateStop() {
		return flightDateStop;
	}
	public void setFlightDateStop(String flightDateStop) {
		this.flightDateStop = flightDateStop;
	}
	public String getFlightHourStop() {
		return flightHourStop;
	}
	public void setFlightHourStop(String flightHourStop) {
		this.flightHourStop = flightHourStop;
	}
	public String getFlightPrice() {
		return flightPrice;
	}
	public void setFlightPrice(String flightPrice) {
		this.flightPrice = flightPrice;
	}
	public PlaneTicket toPlaneTicket(){
		PlaneTicket planeTicket = new PlaneTicket();
		planeTicket.setFlightNumber(flightNumber);
		planeTicket.setFlightFrom(flightFrom);
		planeTicket.setFlightTo(flightTo);
		planeTicket.setFlightDateStart(flightDateStart);
		planeTicket.setFlightHourStart(flightHourStart);
		planeTicket.setFlightDateStop(flightDateStop);
		planeTicket.setFlightHourStop(flightHourStop);
		planeTicket.setFlightPrice(flightPrice);
		
		return planeTicket;

				
	}
	
}
