package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.myPackage.core.entities.AccountRole;
import com.myPackage.core.repositories.AccountRoleRepository;

public class JpaAccountRoleRepository implements AccountRoleRepository {


	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AccountRole addAccountRole(AccountRole data) {
		entityManager.persist(data);
		return data;		
	}

	@Override
	public List<AccountRole> findAllAccountRoles() {
        Query query = entityManager.createQuery("SELECT a FROM AccountRole a order by id");
        return query.getResultList();
	}

	@Override
	public AccountRole removeAccountRole(int id) {
		AccountRole accountRole = entityManager.find(AccountRole.class, id);
		entityManager.remove(accountRole);
		return accountRole;		
	}

	@Override
	public AccountRole getAccountRole(int id) {
		return entityManager.find(AccountRole.class, id);

	}

	@Override
	public AccountRole findRoleByName(String role) {
        Query query = entityManager.createQuery("SELECT a FROM AccountRole a WHERE a.role=?1");
        query.setParameter(1, role);
        List<AccountRole> roles = query.getResultList();
        if(roles.size() == 0) {
            return null;
        } else {
            return roles.get(0);
        }
	}

}
