package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.AccountRole;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.AccountRepository;

@Repository
public class JpaAccountRepository implements AccountRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Account findAccount(Long id) {
		return entityManager.find(Account.class, id);
	}
	
	@Override
	public Account findAccountByLogin(String login) {
        Query query = entityManager.createQuery("SELECT a FROM Account a WHERE a.login=?1");
        query.setParameter(1, login);
        List<Account> accounts = query.getResultList();
        if(accounts.size() == 0) {
            return null;
        } else {
            return accounts.get(0);
        }
	}
	
	@Override
	public List<Account> findAllAccounts() {
        Query query = entityManager.createQuery("SELECT a FROM Account a order by id");
        return query.getResultList();
	}



	@Override
	public Account createAccount(Account data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public Account deleteAccount(Long id) {
		Account account = entityManager.find(Account.class, id);
		entityManager.remove(account);
		return account;		
	}



}
