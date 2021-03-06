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
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.PlaneTicketOrderAlreadyExistsException;
import com.myPackage.core.services.exceptions.PlaneTicketOrderNotFoundException;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.rest.exceptions.BadRequestException;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.exceptions.NotFoundException;
import com.myPackage.rest.resources.PlaneTicketOrderListResource;
import com.myPackage.rest.resources.PlaneTicketOrderResource;
import com.myPackage.rest.resources.asm.PlaneTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketOrderResourceAsm;
import com.myPackage.rest.validators.PlaneTicketOrderValidator;

@RestController
@RequestMapping(value = "/rest/planeTicketOrders")
public class PlaneTicketOrderController {
	
	@Autowired
	private PlaneTicketOrderService planeTicketOrderService;
	
	@Autowired
	private PlaneTicketService planeTicketService;
	
	@Autowired
	private AccountService accountService;
	
	PlaneTicketOrderValidator planeTicketOrderValidator;
	
	public PlaneTicketOrderController() {
		planeTicketOrderValidator = new PlaneTicketOrderValidator();
	}
	
	@Autowired
	public PlaneTicketOrderController(PlaneTicketOrderService planeTicketOrderService) {
		this.planeTicketOrderService = planeTicketOrderService;
		planeTicketOrderValidator = new PlaneTicketOrderValidator();

	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(planeTicketOrderValidator);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketOrderListResource> findAllPlaneTicketOrders() {
		PlaneTicketOrderList planeTicketOrderList = planeTicketOrderService.findAllPlaneTicketOrders();
		PlaneTicketOrderListResource planeTicketOrderListResource = new PlaneTicketOrderListResourceAsm().toResource(planeTicketOrderList);
		return new ResponseEntity<PlaneTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{planeTicketOrderId}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketOrderResource> getPlaneTicketOrder(@PathVariable Long planeTicketOrderId) {
		try {
			PlaneTicketOrder planeTicketOrder = planeTicketOrderService.findPlaneTicketOrder(planeTicketOrderId);
			PlaneTicketOrderResource planeTicketOrderResource = new PlaneTicketOrderResourceAsm()
					.toResource(planeTicketOrder);
			return new ResponseEntity<PlaneTicketOrderResource>(planeTicketOrderResource, HttpStatus.OK);
		} catch (PlaneTicketOrderNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public ResponseEntity<PlaneTicketOrderResource> createPlaneTicketOrder(@Valid @RequestBody PlaneTicketOrderResource sentPlaneTicketOrder) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			PlaneTicketOrder createdPlaneTicketOrder;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!(auth instanceof AnonymousAuthenticationToken) && principal instanceof UserDetails) {//if user is logged in - has auth
				UserDetails details = (UserDetails)principal;	
				Account loggedIn = accountService.findAccountByLogin(details.getUsername());	 
				createdPlaneTicketOrder = planeTicketOrderService.createPlaneTicketOrder(sentPlaneTicketOrder
						.toPlaneTicketOrder(planeTicketService
								.findPlaneTicket(sentPlaneTicketOrder
										.getPlaneTicketId())
								,accountService.findAccount(loggedIn.getId())));
			}else{
				createdPlaneTicketOrder = planeTicketOrderService.createPlaneTicketOrder(sentPlaneTicketOrder
						.toPlaneTicketOrder(planeTicketService
								.findPlaneTicket(sentPlaneTicketOrder
										.getPlaneTicketId())));
			}

			PlaneTicketOrderResource createdPlaneTicketOrderResource = new PlaneTicketOrderResourceAsm().toResource(createdPlaneTicketOrder);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdPlaneTicketOrderResource.getLink("self").getHref()));
			return new ResponseEntity<PlaneTicketOrderResource>(createdPlaneTicketOrderResource, headers, HttpStatus.CREATED);
		} catch (PlaneTicketOrderAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}

	@RequestMapping(value = "/{planeTicketOrderId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlaneTicketOrderResource> deletePlaneTicketOrder(@PathVariable Long planeTicketOrderId) {
		try {
			PlaneTicketOrder planeTicketOrder = planeTicketOrderService.deletePlaneTicketOrder(planeTicketOrderId);

			PlaneTicketOrderResource res = new PlaneTicketOrderResourceAsm().toResource(planeTicketOrder);
			return new ResponseEntity<PlaneTicketOrderResource>(res, HttpStatus.OK);
		} catch (PlaneTicketOrderNotFoundException e) {
			throw new NotFoundException(e);
		}
	}
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@RequestMapping(value = "/myPlaneOrders", method = RequestMethod.GET)
	public ResponseEntity<PlaneTicketOrderListResource> findAllPlaneTicketOrdersForAccount() {
        try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserDetails details = (UserDetails)principal;	
				Account loggedIn = accountService.findAccountByLogin(details.getUsername());	 
			PlaneTicketOrderList planeTicketOrderList = accountService.findAllPlaneTicketOrdersForAccount(loggedIn.getId());
			PlaneTicketOrderListResource planeTicketOrderListResource = new PlaneTicketOrderListResourceAsm().toResource(planeTicketOrderList);
			
	
			return new ResponseEntity<PlaneTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
        } catch(AccountDoesNotExistException exception)
        {
            throw new BadRequestException(exception);
        }
	}
//    @RequestMapping(value="/{planeTicketOrderId}",method = RequestMethod.PUT)
//    public ResponseEntity<PlaneTicketOrderResource> updatePlaneTicketOrder(
//            @PathVariable Long planeTicketOrderId, @RequestBody PlaneTicketOrderResource sentPlaneTicketOrder) {
//        PlaneTicketOrder updatedPlaneTicketOrder = planeTicketOrderService.updatePlaneTicketOrder(planeTicketOrderId, sentPlaneTicketOrder.toPlaneTicketOrder());
//        if(updatedPlaneTicketOrder != null)
//        {
//        	PlaneTicketOrderResource res = new PlaneTicketOrderResourceAsm().toResource(updatedPlaneTicketOrder);
//            return new ResponseEntity<PlaneTicketOrderResource>(res, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<PlaneTicketOrderResource>(HttpStatus.NOT_FOUND);
//        }
//    }
}
