package com.myPackage.rest.mvc;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.exceptions.TrainTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.TrainTicketOrderList;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.resources.TrainTicketOrderListResource;
import com.myPackage.rest.resources.TrainTicketOrderResource;
import com.myPackage.rest.resources.asm.TrainTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketOrderResourceAsm;

@RestController
@RequestMapping(value = "/rest/trainTicketOrders")
public class TrainTicketOrderController {

	private TrainTicketOrderService trainTicketOrderService;

	public TrainTicketOrderController() {
	}
	public TrainTicketOrderController(TrainTicketOrderService TrainTicketOrderService) {
		this.trainTicketOrderService = TrainTicketOrderService;
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<TrainTicketOrderListResource> findAllTrainTicketOrders() {
		TrainTicketOrderList trainTicketOrderList = trainTicketOrderService.findAllTrainTicketOrders();
		TrainTicketOrderListResource trainTicketOrderListResource = new TrainTicketOrderListResourceAsm().toResource(trainTicketOrderList);
		return new ResponseEntity<TrainTicketOrderListResource>(trainTicketOrderListResource, HttpStatus.OK);
	}
	@RequestMapping(value = "/{TrainTicketOrderId}", method = RequestMethod.GET)
	public ResponseEntity<TrainTicketOrderResource> getTrainTicketOrder(@PathVariable Long TrainTicketOrderId) {
		TrainTicketOrder trainTicketOrder = trainTicketOrderService.findTrainTicketOrder(TrainTicketOrderId);
		if(trainTicketOrder != null){
			TrainTicketOrderResource trainTicketOrderResource = new TrainTicketOrderResourceAsm().toResource(trainTicketOrder);
			return new ResponseEntity<TrainTicketOrderResource>(trainTicketOrderResource, HttpStatus.OK);
		}else return new ResponseEntity<TrainTicketOrderResource>(HttpStatus.NOT_FOUND);
			
	}
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TrainTicketOrderResource> createTrainTicketOrder(@RequestBody TrainTicketOrderResource sentTrainTicketOrder) {
		TrainTicketOrder createdTrainTicketOrder = null;
		try {
			createdTrainTicketOrder = trainTicketOrderService.createTrainTicketOrder(sentTrainTicketOrder.toTrainTicketOrder());
			TrainTicketOrderResource createdTrainTicketOrderResource = new TrainTicketOrderResourceAsm().toResource(createdTrainTicketOrder);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdTrainTicketOrderResource.getLink("self").getHref()));
			return new ResponseEntity<TrainTicketOrderResource>(createdTrainTicketOrderResource, headers, HttpStatus.CREATED);
		} catch (TrainTicketOrderAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}
    @RequestMapping(value = "/{TrainTicketOrderId}",method = RequestMethod.DELETE)
    public ResponseEntity<TrainTicketOrderResource> deleteTrainTicketOrder(@PathVariable Long TrainTicketOrderId) {
    	TrainTicketOrder trainTicketOrder = trainTicketOrderService.deleteTrainTicketOrder(TrainTicketOrderId);
        if(trainTicketOrder != null)
        {
        	TrainTicketOrderResource res = new TrainTicketOrderResourceAsm().toResource(trainTicketOrder);
            return new ResponseEntity<TrainTicketOrderResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<TrainTicketOrderResource>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value="/{TrainTicketOrderId}",method = RequestMethod.PUT)
    public ResponseEntity<TrainTicketOrderResource> updateTrainTicketOrder(
            @PathVariable Long TrainTicketOrderId, @RequestBody TrainTicketOrderResource sentTrainTicketOrder) {
        TrainTicketOrder updatedTrainTicketOrder = trainTicketOrderService.updateTrainTicketOrder(TrainTicketOrderId, sentTrainTicketOrder.toTrainTicketOrder());
        if(updatedTrainTicketOrder != null)
        {
        	TrainTicketOrderResource res = new TrainTicketOrderResourceAsm().toResource(updatedTrainTicketOrder);
            return new ResponseEntity<TrainTicketOrderResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<TrainTicketOrderResource>(HttpStatus.NOT_FOUND);
        }
    }
}
