package com.myPackage.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.repositories.TrainTicketRepository;
import com.myPackage.core.services.TrainTicketService;
import com.myPackage.core.services.exceptions.TrainTicketNotFoundException;
import com.myPackage.core.services.util.TrainTicketList;
@Service
@Transactional
public class TrainTicketServiceImplementation implements TrainTicketService{

	@Autowired
	private TrainTicketRepository trainTicketRepository;

	
	@Override
	public TrainTicket createTrainTicket(TrainTicket data) {
//		TrainTicket trainTicket = trainTicketRepository.findTrainTicket(data.getId());
//        if(trainTicket != null)
//        {
//            throw new TrainTicketAlreadyExistsException();
//        }
        return trainTicketRepository.createTrainTicket(data);
	}

	@Override
	public TrainTicketList findAllTrainTickets() {
		 return new TrainTicketList(trainTicketRepository.findAllTrainTickets());

	}

	@Override
	public TrainTicket findTrainTicket(Long id) {
		
		if(trainTicketRepository.findTrainTicket(id) == null){
            throw new TrainTicketNotFoundException();
		}
		return trainTicketRepository.findTrainTicket(id);

	}

	@Override
	public TrainTicket deleteTrainTicket(Long id) {
		TrainTicket ticket = trainTicketRepository.findTrainTicket(id);
		if(ticket == null){
            throw new TrainTicketNotFoundException();
		}
		return trainTicketRepository.deleteTrainTicket(id);

	}

	@Override
	public TrainTicket updateTrainTicket(Long id, TrainTicket data) {
		TrainTicket ticket = trainTicketRepository.findTrainTicket(id);
		if(ticket == null){
            throw new TrainTicketNotFoundException();
		}
		return trainTicketRepository.updateTrainTicket(id, data);
	}

}
