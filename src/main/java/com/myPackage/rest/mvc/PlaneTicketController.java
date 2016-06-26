package com.myPackage.rest.mvc;

import java.net.URI;
import java.util.ArrayList;
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

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.PlaneTicketAlreadyExistsException;
import com.myPackage.core.services.exceptions.PlaneTicketNotFoundException;
import com.myPackage.core.services.util.PlaneTicketList;
import com.myPackage.rest.exceptions.BadRequestException;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.exceptions.NotFoundException;
import com.myPackage.rest.resources.PlaneTicketListResource;
import com.myPackage.rest.resources.PlaneTicketResource;
import com.myPackage.rest.resources.asm.PlaneTicketListResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketResourceAsm;
import com.myPackage.rest.validators.PlaneTicketValidator;

@RestController
@RequestMapping(value = "/rest/planeTickets")
public class PlaneTicketController {
	
	private PlaneTicketService planeTicketService;
	
	private PlaneTicketOrderService planeTicketOrderService;

	PlaneTicketValidator planeTicketValidator;

	public PlaneTicketController() {
		planeTicketValidator = new PlaneTicketValidator();
	}
	
	@Autowired
	public PlaneTicketController(PlaneTicketService planeTicketService, PlaneTicketOrderService planeTicketOrderService) {
		this.planeTicketService = planeTicketService;
		this.planeTicketOrderService = planeTicketOrderService;
		planeTicketValidator = new PlaneTicketValidator();

	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(planeTicketValidator);
	}
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ResponseEntity<PlaneTicketListResource> findAllPlaneTickets() {
		PlaneTicketList planeTicketList = planeTicketService.findAllPlaneTickets();
		PlaneTicketListResource planeTicketListResource = new PlaneTicketListResourceAsm().toResource(planeTicketList);
		return new ResponseEntity<PlaneTicketListResource>(planeTicketListResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{planeTicketId}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ResponseEntity<PlaneTicketResource> getPlaneTicket(@PathVariable Long planeTicketId) {
		try {
			PlaneTicket planeTicket = planeTicketService.findPlaneTicket(planeTicketId);
				PlaneTicketResource planeTicketResource = new PlaneTicketResourceAsm().toResource(planeTicket);
				return new ResponseEntity<PlaneTicketResource>(planeTicketResource, HttpStatus.OK);
		} catch (PlaneTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketResource> createPlaneTicket(@Valid @RequestBody PlaneTicketResource sentPlaneTicket) {
		try {
			PlaneTicket createdPlaneTicket = planeTicketService.createPlaneTicket(sentPlaneTicket.toPlaneTicket());
			PlaneTicketResource createdPlaneTicketResource = new PlaneTicketResourceAsm().toResource(createdPlaneTicket);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdPlaneTicketResource.getLink("self").getHref()));
			return new ResponseEntity<PlaneTicketResource>(createdPlaneTicketResource, headers, HttpStatus.CREATED);
		} catch (PlaneTicketAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}

	@RequestMapping(value = "/{planeTicketId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketResource> deletePlaneTicket(@PathVariable Long planeTicketId) {

		try {

			// delete orders with no users
			List<PlaneTicketOrder> planeTicketOrderList2 = planeTicketOrderService.findAllPlaneTicketOrders()
					.getPlaneTicketOrders();

			for (Iterator it3 = planeTicketOrderList2.iterator(); it3.hasNext();) {
				PlaneTicketOrder planeTicketOrder2 = (PlaneTicketOrder) it3.next();

				if (planeTicketOrder2.getPlaneTicket().getId() == planeTicketId) {

					planeTicketOrderService.deletePlaneTicketOrder(planeTicketOrder2.getId());

				}

			}
			PlaneTicket planeTicket = planeTicketService.deletePlaneTicket(planeTicketId);
			PlaneTicketResource res = new PlaneTicketResourceAsm().toResource(planeTicket);
			return new ResponseEntity<PlaneTicketResource>(res, HttpStatus.OK);
		} catch (PlaneTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
	}

	@RequestMapping(value = "/{planeTicketId}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketResource> updatePlaneTicket(@PathVariable Long planeTicketId,
			@Valid @RequestBody PlaneTicketResource sentPlaneTicket) {
		try {
			PlaneTicket updatedPlaneTicket = planeTicketService.updatePlaneTicket(planeTicketId,
					sentPlaneTicket.toPlaneTicket());
			PlaneTicketResource res = new PlaneTicketResourceAsm().toResource(updatedPlaneTicket);
			return new ResponseEntity<PlaneTicketResource>(res, HttpStatus.OK);

		} catch (PlaneTicketNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
}
