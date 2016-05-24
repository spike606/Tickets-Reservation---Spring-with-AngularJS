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

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.PlaneTicketAlreadyExistsException;
import com.myPackage.core.services.util.PlaneTicketList;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.resources.PlaneTicketListResource;
import com.myPackage.rest.resources.PlaneTicketResource;
import com.myPackage.rest.resources.asm.PlaneTicketListResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketResourceAsm;

@RestController
@RequestMapping(value = "/rest/planeTickets")
public class PlaneTicketController {

	private PlaneTicketService planeTicketService;

	public PlaneTicketController(PlaneTicketService planeTicketService) {
		this.planeTicketService = planeTicketService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PlaneTicketListResource> findAllPlaneTickets() {
		PlaneTicketList planeTicketList = planeTicketService.findAllPlaneTickets();
		PlaneTicketListResource planeTicketListResource = new PlaneTicketListResourceAsm().toResource(planeTicketList);
		return new ResponseEntity<PlaneTicketListResource>(planeTicketListResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{planeTicketId}", method = RequestMethod.GET)
	public ResponseEntity<PlaneTicketResource> getPlaneTicket(@PathVariable Long planeTicketId) {
		PlaneTicket planeTicket = planeTicketService.findPlaneTicket(planeTicketId);
		if(planeTicket != null){
			PlaneTicketResource planeTicketResource = new PlaneTicketResourceAsm().toResource(planeTicket);
			return new ResponseEntity<PlaneTicketResource>(planeTicketResource, HttpStatus.OK);
		}else return new ResponseEntity<PlaneTicketResource>(HttpStatus.NOT_FOUND);
			
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PlaneTicketResource> createPlaneTicket(@RequestBody PlaneTicketResource sentPlaneTicket) {
		PlaneTicket createdPlaneTicket = null;
		try {
			createdPlaneTicket = planeTicketService.createPlaneTicket(sentPlaneTicket.toPlaneTicket());
			PlaneTicketResource createdPlaneTicketResource = new PlaneTicketResourceAsm().toResource(createdPlaneTicket);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdPlaneTicketResource.getLink("self").getHref()));
			return new ResponseEntity<PlaneTicketResource>(createdPlaneTicketResource, headers, HttpStatus.CREATED);
		} catch (PlaneTicketAlreadyExistsException e) {
            throw new ConflictException(e);
		}
	}
    @RequestMapping(value = "/{planeTicketId}",method = RequestMethod.DELETE)
    public ResponseEntity<PlaneTicketResource> deletePlaneTicket(@PathVariable Long planeTicketId) {
    	PlaneTicket planeTicket = planeTicketService.deletePlaneTicket(planeTicketId);
        if(planeTicket != null)
        {
        	PlaneTicketResource res = new PlaneTicketResourceAsm().toResource(planeTicket);
            return new ResponseEntity<PlaneTicketResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<PlaneTicketResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{planeTicketId}",method = RequestMethod.PUT)
    public ResponseEntity<PlaneTicketResource> updatePlaneTicket(
            @PathVariable Long planeTicketId, @RequestBody PlaneTicketResource sentPlaneTicket) {
        PlaneTicket updatedPlaneTicket = planeTicketService.updatePlaneTicket(planeTicketId, sentPlaneTicket.toPlaneTicket());
        if(updatedPlaneTicket != null)
        {
        	PlaneTicketResource res = new PlaneTicketResourceAsm().toResource(updatedPlaneTicket);
            return new ResponseEntity<PlaneTicketResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<PlaneTicketResource>(HttpStatus.NOT_FOUND);
        }
    }
}
