package com.myPackage.rest.mvc;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.services.TrainTicketService;
import com.myPackage.core.services.exceptions.TrainTicketAlreadyExistsException;
import com.myPackage.core.services.util.TrainTicketList;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.resources.TrainTicketListResource;
import com.myPackage.rest.resources.TrainTicketResource;
import com.myPackage.rest.resources.asm.TrainTicketListResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketResourceAsm;
import com.myPackage.rest.validators.TrainTicketValidator;

@RestController
@RequestMapping(value = "/rest/trainTickets")
public class TrainTicketController {
	@Autowired
	private TrainTicketService trainTicketService;

	TrainTicketValidator trainTicketValidator;
	
	public TrainTicketController() {
		trainTicketValidator = new TrainTicketValidator();
	}
	
	@Autowired
	public TrainTicketController(TrainTicketService trainTicketService) {
		this.trainTicketService = trainTicketService;
		trainTicketValidator = new TrainTicketValidator();

	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(trainTicketValidator);
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<TrainTicketListResource> findAllTrainTickets() {
		TrainTicketList trainTicketList = trainTicketService.findAllTrainTickets();
		TrainTicketListResource trainTicketListResource = new TrainTicketListResourceAsm().toResource(trainTicketList);
		return new ResponseEntity<TrainTicketListResource>(trainTicketListResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{TrainTicketId}", method = RequestMethod.GET)
	public ResponseEntity<TrainTicketResource> getTrainTicket(@PathVariable Long TrainTicketId) {
		TrainTicket TrainTicket = trainTicketService.findTrainTicket(TrainTicketId);
		if(TrainTicket != null){
			TrainTicketResource TrainTicketResource = new TrainTicketResourceAsm().toResource(TrainTicket);
			return new ResponseEntity<TrainTicketResource>(TrainTicketResource, HttpStatus.OK);
		}else return new ResponseEntity<TrainTicketResource>(HttpStatus.NOT_FOUND);
			
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TrainTicketResource> createTrainTicket(@Valid @RequestBody TrainTicketResource sentTrainTicket) {
		try {
			TrainTicket	createdTrainTicket = trainTicketService.createTrainTicket(sentTrainTicket.toTrainTicket());
			TrainTicketResource createdTrainTicketResource = new TrainTicketResourceAsm().toResource(createdTrainTicket);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdTrainTicketResource.getLink("self").getHref()));
			return new ResponseEntity<TrainTicketResource>(createdTrainTicketResource, headers, HttpStatus.CREATED);
		} catch (TrainTicketAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}
    @RequestMapping(value = "/{TrainTicketId}",method = RequestMethod.DELETE)
    public ResponseEntity<TrainTicketResource> deleteTrainTicket(@PathVariable Long TrainTicketId) {
    	TrainTicket TrainTicket = trainTicketService.deleteTrainTicket(TrainTicketId);
        if(TrainTicket != null)
        {
        	TrainTicketResource res = new TrainTicketResourceAsm().toResource(TrainTicket);
            return new ResponseEntity<TrainTicketResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<TrainTicketResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{TrainTicketId}",method = RequestMethod.PUT)
    public ResponseEntity<TrainTicketResource> updateTrainTicket(
            @PathVariable Long TrainTicketId,@Valid @RequestBody TrainTicketResource sentTrainTicket) {
        TrainTicket updatedTrainTicket = trainTicketService.updateTrainTicket(TrainTicketId, sentTrainTicket.toTrainTicket());
        if(updatedTrainTicket != null)
        {
        	TrainTicketResource res = new TrainTicketResourceAsm().toResource(updatedTrainTicket);
            return new ResponseEntity<TrainTicketResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<TrainTicketResource>(HttpStatus.NOT_FOUND);
        }
    }
	
}
