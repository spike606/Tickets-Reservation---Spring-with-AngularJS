package com.myPackage.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.myPackage.core.entities.Account;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private AccountService service;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		try {
			Account account = service.findAccountByLogin(login);
			return new AccountUserDetails(account);
		} catch (AccountDoesNotExistException exc) {
			throw new UsernameNotFoundException("no user found with " + login);
		}

	}
}