package com.myPackage.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.entities.TrainTicketOrder;

public class TrainTicketOrderResource extends ResourceSupport {


	private String firstname;
	private String secondname;
	private String lastname;
	private String country;
	private String state;
	private String city;
	private String street;
	private String email;
	private String telephone;
	private TrainTicket trainTicket;
	private Account owner;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public TrainTicket getTrainTicket() {
		return trainTicket;
	}
	public void setTrainTicket(TrainTicket trainTicket) {
		this.trainTicket = trainTicket;
	}
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	public TrainTicketOrder toTrainTicketOrder() {
		
		TrainTicketOrder trainTicketOrder = new TrainTicketOrder();
		trainTicketOrder.setFirstname(firstname);
		trainTicketOrder.setSecondname(secondname);
		trainTicketOrder.setLastname(lastname);
		trainTicketOrder.setEmail(email);
		trainTicketOrder.setTelephone(telephone);
		trainTicketOrder.setCountry(country);
		trainTicketOrder.setCity(city);
		trainTicketOrder.setState(state);
		trainTicketOrder.setStreet(street);
		trainTicketOrder.setTrainTicket(trainTicket);
		trainTicketOrder.setOwner(owner);

		return trainTicketOrder;

	}
	
}
