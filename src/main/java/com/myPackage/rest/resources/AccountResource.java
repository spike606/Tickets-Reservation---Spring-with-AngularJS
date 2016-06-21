package com.myPackage.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myPackage.core.entities.Account;

public class AccountResource  extends ResourceSupport{

	private String firstname;
	private String secondname;
	private String lastname;

	private String email;
	private String telephone;

	private String country;

	private String state;
	private String city;
	private String street;

	private String login;
	private String password;

	private Long rid;
	
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	@JsonIgnore//ignore when sending data to client
	public String getPassword() {
		return password;
	}

	@JsonProperty//password is still a property
	public void setPassword(String password) {
		this.password = password;
	}

	public Account toAccount() {
		
		Account account = new Account();
		account.setFirstname(firstname);
		account.setSecondname(secondname);
		account.setLastname(lastname);
		account.setEmail(email);
		account.setTelephone(telephone);
		account.setCountry(country);
		account.setCity(city);
		account.setState(state);
		account.setStreet(street);
		account.setLogin(login);
		account.setPassword(password);

		return account;

	}
}
