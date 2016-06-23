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

import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.PlaneTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.rest.exceptions.ConflictException;
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
	public ResponseEntity<PlaneTicketOrderListResource> findAllPlaneTicketOrders() {
		PlaneTicketOrderList planeTicketOrderList = planeTicketOrderService.findAllPlaneTicketOrders();
		PlaneTicketOrderListResource planeTicketOrderListResource = new PlaneTicketOrderListResourceAsm().toResource(planeTicketOrderList);
		return new ResponseEntity<PlaneTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
	}
	@RequestMapping(value = "/{planeTicketOrderId}", method = RequestMethod.GET)
	public ResponseEntity<PlaneTicketOrderResource> getPlaneTicketOrder(@PathVariable Long planeTicketOrderId) {
		PlaneTicketOrder planeTicketOrder = planeTicketOrderService.findPlaneTicketOrder(planeTicketOrderId);
		if(planeTicketOrder != null){
			PlaneTicketOrderResource planeTicketOrderResource = new PlaneTicketOrderResourceAsm().toResource(planeTicketOrder);
			return new ResponseEntity<PlaneTicketOrderResource>(planeTicketOrderResource, HttpStatus.OK);
		}else return new ResponseEntity<PlaneTicketOrderResource>(HttpStatus.NOT_FOUND);
			
	}
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PlaneTicketOrderResource> createPlaneTicketOrder(@Valid @RequestBody PlaneTicketOrderResource sentPlaneTicketOrder) {
//		PlaneTicketOrder createdPlaneTicketOrder = null;
		try {
			PlaneTicketOrder createdPlaneTicketOrder = planeTicketOrderService.createPlaneTicketOrder(sentPlaneTicketOrder.toPlaneTicketOrder(
					planeTicketService.findPlaneTicket(sentPlaneTicketOrder.getPlaneTicketId())));
			PlaneTicketOrderResource createdPlaneTicketOrderResource = new PlaneTicketOrderResourceAsm().toResource(createdPlaneTicketOrder);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdPlaneTicketOrderResource.getLink("self").getHref()));
			return new ResponseEntity<PlaneTicketOrderResource>(createdPlaneTicketOrderResource, headers, HttpStatus.CREATED);
		} catch (PlaneTicketOrderAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}
    @RequestMapping(value = "/{planeTicketOrderId}",method = RequestMethod.DELETE)
    public ResponseEntity<PlaneTicketOrderResource> deletePlaneTicketOrder(@PathVariable Long planeTicketOrderId) {
    	PlaneTicketOrder planeTicketOrder = planeTicketOrderService.deletePlaneTicketOrder(planeTicketOrderId);
        if(planeTicketOrder != null)
        {
        	PlaneTicketOrderResource res = new PlaneTicketOrderResourceAsm().toResource(planeTicketOrder);
            return new ResponseEntity<PlaneTicketOrderResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<PlaneTicketOrderResource>(HttpStatus.NOT_FOUND);
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
