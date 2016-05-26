package com.myPackage.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.AccountRepository;
import com.myPackage.core.repositories.TrainTicketOrderRepository;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.TrainTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.TrainTicketOrderList;
@Service
@Transactional
public class TrainTicketOrderServiceImplemenation  implements TrainTicketOrderService{

	@Autowired
	private TrainTicketOrderRepository trainTicketOrderRepository;
	@Autowired
    private AccountRepository accountRepository;
	
	@Override
	public TrainTicketOrder createTrainTicketOrder(TrainTicketOrder data) {
//		TrainTicketOrder trainTicketOrder = trainTicketOrderRepository.findTrainTicketOrder(data.getId());
//        if(trainTicketOrder != null)
//        {
//            throw new TrainTicketOrderAlreadyExistsException();
//        }
        return trainTicketOrderRepository.createTrainTicketOrder(data);

	}

	@Override
	public TrainTicketOrderList findAllTrainTicketOrders() {
		TrainTicketOrderList list = new TrainTicketOrderList();
		list.setTrainTicketOrders(trainTicketOrderRepository.findAllTrainTicketOrders());
		return list; 
	}

	@Override
	public TrainTicketOrder findTrainTicketOrder(Long id) {
		return trainTicketOrderRepository.findTrainTicketOrder(id);

	}

	@Override
	public TrainTicketOrderList findTrainOrdersByAccount(String login) {
		Account account = accountRepository.findAccountByLogin(login);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
        TrainTicketOrderList list = new TrainTicketOrderList();
		list.setTrainTicketOrders(trainTicketOrderRepository.findAllTrainTicketOrdersForAccount(account.getId()));

		return list;
	}

	@Override
	public TrainTicketOrder deleteTrainTicketOrder(Long id) {
		return trainTicketOrderRepository.deleteTrainTicketOrder(id);

	}

}
