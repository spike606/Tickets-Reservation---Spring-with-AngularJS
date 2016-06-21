package com.myPackage.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.Account;
import com.myPackage.rest.mvc.AccountController;
import com.myPackage.rest.resources.AccountResource;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource>{

	public AccountResourceAsm() {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setFirstname(account.getFirstname());
		res.setSecondname(account.getSecondname());
		res.setLastname(account.getLastname());
		res.setEmail(account.getEmail());
		res.setTelephone(account.getTelephone());
		res.setCountry(account.getCountry());
		res.setCity(account.getCity());
		res.setState(account.getState());
		res.setStreet(account.getStreet());
		res.setLogin(account.getLogin());
		res.setPassword(account.getPassword());
		res.setRid(account.getId());
		Link link = linkTo(AccountController.class).slash(account.getId()).withSelfRel();
		res.add(link);
        return res;
	}

}
