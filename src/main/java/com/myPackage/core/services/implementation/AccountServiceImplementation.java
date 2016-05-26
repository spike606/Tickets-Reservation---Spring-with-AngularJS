package com.myPackage.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.AccountRepository;
import com.myPackage.core.repositories.PlaneTicketOrderRepository;
import com.myPackage.core.repositories.TrainTicketOrderRepository;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.exceptions.AccountAlreadyExistsException;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.util.AccountList;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;

@Service
@Transactional
public class AccountServiceImplementation implements AccountService{

	@Autowired
    private AccountRepository accountRepository;
	@Autowired
    private PlaneTicketOrderRepository planeTicketOrderRepository;
	@Autowired
    private TrainTicketOrderRepository trainTicketOrderRepository;

	
	@Override
	public Account findAccount(Long id) {
		 return accountRepository.findAccount(id);
	}

	@Override
	public AccountList findAllAccounts() {
		return new AccountList(accountRepository.findAllAccounts());
	}

	@Override
	public PlaneTicketOrder createPlaneTicketOrderForAccount(Long accountId, PlaneTicketOrder data) {
//		PlaneTicketOrder order = planeTicketOrderRepository.findPlaneTicketOrder(data.getId());
//		
//        if(order != null)
//        {
//            throw new PlaneTicketOrderAlreadyExistsException();
//        }

        Account account = accountRepository.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }

        PlaneTicketOrder newOrder = planeTicketOrderRepository.createPlaneTicketOrder(data);

        newOrder.setOwner(account);

        return newOrder;
	}

	@Override
	public TrainTicketOrder createTrainTicketOrderForAccount(Long accountId, TrainTicketOrder data) {
//		TrainTicketOrder order = trainTicketOrderRepository.findTrainTicketOrder(data.getId());
//		
//        if(order != null)
//        {
//            throw new PlaneTicketOrderAlreadyExistsException();
//        }

        Account account = accountRepository.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }

        TrainTicketOrder newOrder = trainTicketOrderRepository.createTrainTicketOrder(data);

        newOrder.setOwner(account);

        return newOrder;
	}

	@Override
	public PlaneTicketOrderList findAllPlaneTicketOrdersForAccount(Long accountId) {
		 Account account = accountRepository.findAccount(accountId);
	        if(account == null)
	        {
	            throw new AccountDoesNotExistException();
	        }
	        return new PlaneTicketOrderList(planeTicketOrderRepository.findAllPlaneTicketOrdersForAccount(accountId));
	}

	@Override
	public TrainTicketOrderList findAllTrainTicketOrdersForAccount(Long accountId) {
		 Account account = accountRepository.findAccount(accountId);
	        if(account == null)
	        {
	            throw new AccountDoesNotExistException();
	        }
	        return new TrainTicketOrderList(trainTicketOrderRepository.findAllTrainTicketOrdersForAccount(accountId));
	
	}

	@Override
	public Account createAccount(Account data) {
        Account account = accountRepository.findAccountByLogin(data.getLogin());
        if(account != null)
        {
            throw new AccountAlreadyExistsException();
        }
        return accountRepository.createAccount(data);
	}

	@Override
	public Account findAccountByLogin(String login) {
		 return accountRepository.findAccountByLogin(login);
	}

	@Override
	public Account deleteAccount(Long id) {
		return accountRepository.deleteAccount(id);
	}

}
