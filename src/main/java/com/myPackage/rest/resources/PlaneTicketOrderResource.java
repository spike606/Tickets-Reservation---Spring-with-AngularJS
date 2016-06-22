package com.myPackage.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.repositories.PlaneTicketRepository;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.implementation.PlaneTicketServiceImplementation;

@Service
public class PlaneTicketOrderResource extends ResourceSupport{
	
	@Autowired
	private PlaneTicketService planeTicketService;

	
	private String firstname;
	private String secondname;
	private String lastname;
	private String country;
	private String state;
	private String city;
	private String street;
	private String email;
	private String telephone;
	private Long planeTicketId;
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
	public Long getPlaneTicketId() {
		return planeTicketId;
	}
	public void setPlaneTicket(Long planeTicketId) {
		this.planeTicketId = planeTicketId;
	}
	public Account getOwner() {
		return owner;
	}
	public void setOwner(Account owner) {
		this.owner = owner;
	}
	public PlaneTicketOrder toPlaneTicketOrder(PlaneTicket planeTicket) {
		
		PlaneTicketOrder planeTicketOrder = new PlaneTicketOrder();
		planeTicketOrder.setFirstname(firstname);
		planeTicketOrder.setSecondname(secondname);
		planeTicketOrder.setLastname(lastname);
		planeTicketOrder.setEmail(email);
		planeTicketOrder.setTelephone(telephone);
		planeTicketOrder.setCountry(country);
		planeTicketOrder.setCity(city);
		planeTicketOrder.setState(state);
		planeTicketOrder.setStreet(street);
//		System.out.println("************************************" + planeTicketService.findPlaneTicket(new Long(50)).getId());
		planeTicketOrder.setPlaneTicket(planeTicket);
		planeTicketOrder.setOwner(owner);
//		System.out.println("dAne----------------------------------------------------- " + planeTicket);
//		System.out.println(planeTicket.getFlightNumber());


		return planeTicketOrder;

	}
	
}
