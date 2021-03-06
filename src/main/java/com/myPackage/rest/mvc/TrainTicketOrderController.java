package com.myPackage.rest.mvc;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.TrainTicketService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.TrainTicketOrderAlreadyExistsException;
import com.myPackage.core.services.exceptions.TrainTicketOrderNotFoundException;
import com.myPackage.core.services.util.TrainTicketOrderList;
import com.myPackage.rest.exceptions.BadRequestException;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.exceptions.NotFoundException;
import com.myPackage.rest.resources.TrainTicketOrderListResource;
import com.myPackage.rest.resources.TrainTicketOrderResource;
import com.myPackage.rest.resources.asm.TrainTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketOrderResourceAsm;
import com.myPackage.rest.validators.TrainTicketOrderValidator;

@RestController
@RequestMapping(value = "/rest/trainTicketOrders")
public class TrainTicketOrderController {
	
	@Autowired
	private TrainTicketOrderService trainTicketOrderService;
	
	@Autowired
	private TrainTicketService trainTicketService;
	
	@Autowired
	private AccountService accountService;

	TrainTicketOrderValidator trainTicketOrderValidator;

	public TrainTicketOrderController() {
		trainTicketOrderValidator = new TrainTicketOrderValidator();
	}
	
	
	@Autowired
	public TrainTicketOrderController(TrainTicketOrderService TrainTicketOrderService) {
		this.trainTicketOrderService = TrainTicketOrderService;
		trainTicketOrderValidator = new TrainTicketOrderValidator();

	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(trainTicketOrderValidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TrainTicketOrderListResource> findAllTrainTicketOrders() {
		TrainTicketOrderList trainTicketOrderList = trainTicketOrderService.findAllTrainTicketOrders();
		TrainTicketOrderListResource trainTicketOrderListResource = new TrainTicketOrderListResourceAsm().toResource(trainTicketOrderList);
		return new ResponseEntity<TrainTicketOrderListResource>(trainTicketOrderListResource, HttpStatus.OK);
	}
	@RequestMapping(value = "/{TrainTicketOrderId}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TrainTicketOrderResource> getTrainTicketOrder(@PathVariable Long TrainTicketOrderId) {
		try{
			TrainTicketOrder trainTicketOrder = trainTicketOrderService.findTrainTicketOrder(TrainTicketOrderId);
		
			TrainTicketOrderResource trainTicketOrderResource = new TrainTicketOrderResourceAsm().toResource(trainTicketOrder);
			return new ResponseEntity<TrainTicketOrderResource>(trainTicketOrderResource, HttpStatus.OK);
		} catch (TrainTicketOrderNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public ResponseEntity<TrainTicketOrderResource> createTrainTicketOrder(@Valid @RequestBody TrainTicketOrderResource sentTrainTicketOrder) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			TrainTicketOrder createdTrainTicketOrder;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!(auth instanceof AnonymousAuthenticationToken) && principal instanceof UserDetails) {//if user is logged in - has auth
			 UserDetails details = (UserDetails)principal;	
			 Account loggedIn = accountService.findAccountByLogin(details.getUsername());
			 
			createdTrainTicketOrder = trainTicketOrderService.createTrainTicketOrder(sentTrainTicketOrder.
					toTrainTicketOrder(trainTicketService.findTrainTicket(sentTrainTicketOrder.getTrainTicketId()),accountService.findAccount(loggedIn.getId())));
			}else{
				createdTrainTicketOrder = trainTicketOrderService.createTrainTicketOrder(sentTrainTicketOrder.
						toTrainTicketOrder(trainTicketService.findTrainTicket(sentTrainTicketOrder.getTrainTicketId())));
				
			}
			TrainTicketOrderResource createdTrainTicketOrderResource = new TrainTicketOrderResourceAsm().toResource(createdTrainTicketOrder);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdTrainTicketOrderResource.getLink("self").getHref()));
			return new ResponseEntity<TrainTicketOrderResource>(createdTrainTicketOrderResource, headers, HttpStatus.CREATED);
		} catch (TrainTicketOrderAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}

	@RequestMapping(value = "/{TrainTicketOrderId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TrainTicketOrderResource> deleteTrainTicketOrder(@PathVariable Long TrainTicketOrderId) {
		try {
			TrainTicketOrder trainTicketOrder = trainTicketOrderService.deleteTrainTicketOrder(TrainTicketOrderId);

			TrainTicketOrderResource res = new TrainTicketOrderResourceAsm().toResource(trainTicketOrder);
			return new ResponseEntity<TrainTicketOrderResource>(res, HttpStatus.OK);
		} catch (TrainTicketOrderNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/myTrainOrders", method = RequestMethod.GET)
	public ResponseEntity<TrainTicketOrderListResource> findAllTrainTicketOrdersForAccount() {
        try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserDetails details = (UserDetails)principal;	
				Account loggedIn = accountService.findAccountByLogin(details.getUsername());	 
			TrainTicketOrderList trainTicketOrderList = accountService.findAllTrainTicketOrdersForAccount(loggedIn.getId());
			TrainTicketOrderListResource planeTicketOrderListResource = new TrainTicketOrderListResourceAsm().toResource(trainTicketOrderList);
			
	
			return new ResponseEntity<TrainTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
        } catch(AccountDoesNotExistException exception)
        {
            throw new BadRequestException(exception);
        }
	}
//    @RequestMapping(value="/{TrainTicketOrderId}",method = RequestMethod.PUT)
//    public ResponseEntity<TrainTicketOrderResource> updateTrainTicketOrder(
//            @PathVariable Long TrainTicketOrderId, @RequestBody TrainTicketOrderResource sentTrainTicketOrder) {
//        TrainTicketOrder updatedTrainTicketOrder = trainTicketOrderService.updateTrainTicketOrder(TrainTicketOrderId, sentTrainTicketOrder.toTrainTicketOrder());
//        if(updatedTrainTicketOrder != null)
//        {
//        	TrainTicketOrderResource res = new TrainTicketOrderResourceAsm().toResource(updatedTrainTicketOrder);
//            return new ResponseEntity<TrainTicketOrderResource>(res, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<TrainTicketOrderResource>(HttpStatus.NOT_FOUND);
//        }
//    }
}
