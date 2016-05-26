package com.myPackage.core.repositories.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.AccountRepository;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;

@Repository
public class JpaAccountRepository implements AccountRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Account findAccount(Long id) {
		return entityManager.find(Account.class, id);
	}

	@Override
	public PlaneTicketOrderList findAllPlaneTicketOrdersForAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrainTicketOrderList findAllTrainTicketOrdersForAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account createAccount(Account data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public PlaneTicketOrder createPlaneTicketOrder(Long planeTicketOrderId, PlaneTicketOrder data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrainTicketOrder createTrainTicketOrder(Long trainTicketOrderId, TrainTicketOrder data) {
		// TODO Auto-generated method stub
		return null;
	}

}
