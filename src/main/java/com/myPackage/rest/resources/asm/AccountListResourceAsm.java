package com.myPackage.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.services.util.AccountList;
import com.myPackage.rest.mvc.AccountController;
import com.myPackage.rest.resources.AccountListResource;
import com.myPackage.rest.resources.AccountResource;

public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountList, AccountListResource>{

	public AccountListResourceAsm() {
		super(AccountController.class, AccountListResource.class);

	}

	@Override
	public AccountListResource toResource(AccountList accountList) {
		List<AccountResource> resources = new AccountResourceAsm().toResources(accountList.getAccounts());
		AccountListResource listResource = new AccountListResource();
		listResource.setAccounts(resources);
		return listResource;
	}

}
