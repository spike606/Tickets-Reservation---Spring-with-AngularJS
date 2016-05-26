package com.myPackage.core.repositories;


import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.myPackage.TicketsServiceApplication;
import com.myPackage.core.entities.Account;

import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketsServiceApplication.class)
@WebAppConfiguration
@IntegrationTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;
	
	private Account account;
	
	
	@Before
	@Transactional 
	@Rollback(false)
	public void setup(){
		account = new Account();
		account.setLogin("testLogin");
		account.setPassword("testPassword");
		repository.createAccount(account);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testGetAccount(){
		assertNotNull(repository.findAccount(account.getId()));
	}
}
