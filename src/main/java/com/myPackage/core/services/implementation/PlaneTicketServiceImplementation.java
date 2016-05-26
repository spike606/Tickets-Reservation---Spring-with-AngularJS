package com.myPackage.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.repositories.PlaneTicketRepository;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.PlaneTicketAlreadyExistsException;
import com.myPackage.core.services.exceptions.PlaneTicketNotFoundException;
import com.myPackage.core.services.util.PlaneTicketList;
@Service
@Transactional
public class PlaneTicketServiceImplementation implements PlaneTicketService{

	@Autowired
	private PlaneTicketRepository planeTicketRepository;
	
	@Override
	public PlaneTicket createPlaneTicket(PlaneTicket data) {
		PlaneTicket planeTicket = planeTicketRepository.findPlaneTicket(data.getId());
        if(planeTicket != null)
        {
            throw new PlaneTicketAlreadyExistsException();
        }
        return planeTicketRepository.createPlaneTicket(data);
	}

	@Override
	public PlaneTicketList findAllPlaneTickets() {
		 return new PlaneTicketList(planeTicketRepository.findAllPlaneTickets());
	}

	@Override
	public PlaneTicket findPlaneTicket(Long id) {
		return planeTicketRepository.findPlaneTicket(id);
	}

	@Override
	public PlaneTicket deletePlaneTicket(Long id) {
		return planeTicketRepository.deletePlaneTicket(id);
	}

	@Override
	public PlaneTicket updatePlaneTicket(Long id, PlaneTicket data) {
		PlaneTicket ticket = planeTicketRepository.findPlaneTicket(id);
		if(ticket == null){
            throw new PlaneTicketNotFoundException();
		}
		return planeTicketRepository.updatePlaneTicket(id, data);
	}

}
