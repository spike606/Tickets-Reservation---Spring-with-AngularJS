package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.AccountRole;

public interface AccountRoleRepository {

	public AccountRole addAccountRole(AccountRole userRole);
	public List<AccountRole> findAllAccountRoles();
	public AccountRole removeAccountRole(int id);
	public AccountRole getAccountRole(int id);
	public AccountRole findRoleByName(String role);
}
