package com.myPackage.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.repositories.AccountRepository;
import com.myPackage.core.repositories.PlaneTicketOrderRepository;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.PlaneTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.PlaneTicketOrderList;
@Service
@Transactional
public class PlaneTicketOrderServiceImplementation implements PlaneTicketOrderService{
	
	@Autowired
	private PlaneTicketOrderRepository planeTicketOrderRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public PlaneTicketOrder createPlaneTicketOrder(PlaneTicketOrder data) {
//		PlaneTicketOrder planeTicketOrder = planeTicketOrderRepository.findPlaneTicketOrder(data.getId());
//        if(planeTicketOrder != null)
//        {
//            throw new PlaneTicketOrderAlreadyExistsException();
//        }
        return planeTicketOrderRepository.createPlaneTicketOrder(data);
	}

	@Override
	public PlaneTicketOrderList findAllPlaneTicketOrders() {
		
		PlaneTicketOrderList list = new PlaneTicketOrderList();
		list.setPlaneTicketOrders(planeTicketOrderRepository.findAllPlaneTicketOrders());
		return list; 
	}

	@Override
	public PlaneTicketOrder findPlaneTicketOrder(Long id) {
		return planeTicketOrderRepository.findPlaneTicketOrder(id);
	}

	@Override
	public PlaneTicketOrderList findPlaneTicketOrdersByAccount(String login) {
		Account account = accountRepository.findAccountByLogin(login);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
		PlaneTicketOrderList list = new PlaneTicketOrderList();
		list.setPlaneTicketOrders(planeTicketOrderRepository.findAllPlaneTicketOrdersForAccount(account.getId()));

		return list;
	}

	@Override
	public PlaneTicketOrder deletePlaneTicketOrder(Long id) {
		return planeTicketOrderRepository.deletePlaneTicketOrder(id);
	}

}
