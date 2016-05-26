package com.myPackage.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class AccountListResource extends ResourceSupport {

	 private List<AccountResource> accounts = new ArrayList<AccountResource>();

	public List<AccountResource> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountResource> accounts) {
		this.accounts = accounts;
	}
	
}
