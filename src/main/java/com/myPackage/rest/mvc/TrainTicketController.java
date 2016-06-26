package com.myPackage.rest.mvc;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.TrainTicketService;
import com.myPackage.core.services.exceptions.TrainTicketAlreadyExistsException;
import com.myPackage.core.services.exceptions.TrainTicketNotFoundException;
import com.myPackage.core.services.util.TrainTicketList;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.exceptions.NotFoundException;
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
	
	private TrainTicketOrderService trainTicketOrderService;

	TrainTicketValidator trainTicketValidator;
	
	public TrainTicketController() {
		trainTicketValidator = new TrainTicketValidator();
	}
	
	@Autowired
	public TrainTicketController(TrainTicketService trainTicketService, TrainTicketOrderService trainTicketOrderService) {
		this.trainTicketService = trainTicketService;
		this.trainTicketOrderService = trainTicketOrderService;
		trainTicketValidator = new TrainTicketValidator();

	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(trainTicketValidator);
	}
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ResponseEntity<TrainTicketListResource> findAllTrainTickets() {
		TrainTicketList trainTicketList = trainTicketService.findAllTrainTickets();
		TrainTicketListResource trainTicketListResource = new TrainTicketListResourceAsm().toResource(trainTicketList);
		return new ResponseEntity<TrainTicketListResource>(trainTicketListResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{TrainTicketId}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ResponseEntity<TrainTicketResource> getTrainTicket(@PathVariable Long TrainTicketId) {
		try{TrainTicket TrainTicket = trainTicketService.findTrainTicket(TrainTicketId);
			TrainTicketResource TrainTicketResource = new TrainTicketResourceAsm().toResource(TrainTicket);
			return new ResponseEntity<TrainTicketResource>(TrainTicketResource, HttpStatus.OK);
		} catch (TrainTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
			
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
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

	@RequestMapping(value = "/{TrainTicketId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TrainTicketResource> deleteTrainTicket(@PathVariable Long TrainTicketId) {

		try {
			// delete orders with no users
			List<TrainTicketOrder> trainTicketOrderList2 = trainTicketOrderService.findAllTrainTicketOrders()
					.getTrainTicketOrders();

			for (Iterator it3 = trainTicketOrderList2.iterator(); it3.hasNext();) {
				TrainTicketOrder trainTicketOrder2 = (TrainTicketOrder) it3.next();

				if (trainTicketOrder2.getTrainTicket().getId() == TrainTicketId) {

					trainTicketOrderService.deleteTrainTicketOrder(trainTicketOrder2.getId());

				}

			}
			TrainTicket TrainTicket = trainTicketService.deleteTrainTicket(TrainTicketId);

			TrainTicketResource res = new TrainTicketResourceAsm().toResource(TrainTicket);
			return new ResponseEntity<TrainTicketResource>(res, HttpStatus.OK);
		} catch (TrainTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
	}

	@RequestMapping(value = "/{TrainTicketId}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TrainTicketResource> updateTrainTicket(@PathVariable Long TrainTicketId,
			@Valid @RequestBody TrainTicketResource sentTrainTicket) {
		try {
			TrainTicket updatedTrainTicket = trainTicketService.updateTrainTicket(TrainTicketId,
					sentTrainTicket.toTrainTicket());

			TrainTicketResource res = new TrainTicketResourceAsm().toResource(updatedTrainTicket);
			return new ResponseEntity<TrainTicketResource>(res, HttpStatus.OK);
		} catch (TrainTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
	
}
