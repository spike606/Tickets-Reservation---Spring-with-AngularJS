package com.myPackage.rest.mvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.services.PlaneTicketService;
import com.myPackage.core.services.exceptions.PlaneTicketAlreadyExistsException;
import com.myPackage.core.services.util.PlaneTicketList;

public class PlaneTicketControllerTest {

	@InjectMocks
	private PlaneTicketController controller;
	
	@Mock
	private PlaneTicketService service;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
    @Test
    public void findAllPlaneTickets() throws Exception {
        List<PlaneTicket> list = new ArrayList<PlaneTicket>();

        
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");
        list.add(ticket1);

        PlaneTicket ticket2 = new PlaneTicket();
        ticket2.setId(2L);
        ticket2.setFlightFrom("Warsaw");
        list.add(ticket2);

        PlaneTicketList allTickets = new PlaneTicketList();
        allTickets.setPlaneTickets(list);

        when(service.findAllPlaneTickets()).thenReturn(allTickets);

        mockMvc.perform(get("/rest/planeTickets"))
                .andExpect(jsonPath("$.planeTickets[*].flightFrom",
                        hasItems(endsWith("Berlin"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getExistingPlaneTicket() throws Exception {
    	PlaneTicket planeTicket = new PlaneTicket();
    	planeTicket.setId(1L);
    	planeTicket.setFlightFrom("Berlin");

        when(service.findPlaneTicket(1L)).thenReturn(planeTicket);

        mockMvc.perform(get("/rest/planeTickets/1"))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/planeTickets/1"))))
                .andExpect(jsonPath("$.flightFrom", is(planeTicket.getFlightFrom())))
                .andExpect(status().isOk());
        //TODO: unit test in planeTicketOrder (check owner)
    }
    
    @Test
    public void getNotExistingPlaneTicket() throws Exception{
        when(service.findPlaneTicket(1L)).thenReturn(null);
        mockMvc.perform(get("/rest/planeTickets/1"))
		.andDo(print())
        .andExpect(status().isNotFound());
    }

    @Test 
    public void createNotExistingPlaneTicket() throws Exception{
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");
        ticket1.setFlightTo("Warsaw");
        ticket1.setFlightNumber("345FT");
        
        when(service.createPlaneTicket(any(PlaneTicket.class))).thenReturn(ticket1);
        mockMvc.perform(post("/rest/planeTickets")
                .content("{\"flightNumber\":\"345FT\",\"flightFrom\":\"Berlin\",\"flightTo\":\"Warsaw\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/planeTickets/1")))
                .andExpect(jsonPath("$.flightNumber", is(ticket1.getFlightNumber())))
                .andExpect(jsonPath("$.flightFrom", is(ticket1.getFlightFrom())))
                .andExpect(jsonPath("$.flightTo", is(ticket1.getFlightTo())))
                .andExpect(status().isCreated());
        
    }
    @Test 
    public void createExistingPlaneTicket() throws Exception{
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");
        ticket1.setFlightTo("Warsaw");
        ticket1.setFlightNumber("345FT");
        
        when(service.createPlaneTicket(any(PlaneTicket.class))).thenThrow(new PlaneTicketAlreadyExistsException());
        mockMvc.perform(post("/rest/planeTickets")
                .content("{\"flightNumber\":\"345FT\",\"flightFrom\":\"Berlin\",\"flightTo\":\"Warsaw\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isConflict());
        
    }
    @Test 
    public void deleteExistingPlaneTicket() throws Exception{
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");
        ticket1.setFlightTo("Warsaw");
        ticket1.setFlightNumber("345FT");
        
        when(service.deletePlaneTicket(eq(1L))).thenReturn(ticket1);
        mockMvc.perform(delete("/rest/planeTickets/1"))
                .andExpect(jsonPath("$.flightFrom", is(ticket1.getFlightFrom())))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/planeTickets/1"))))
        		.andDo(print())
                .andExpect(status().isOk());
        
    }
    @Test 
    public void deleteNotExistingPlaneTicket() throws Exception{
        
        when(service.deletePlaneTicket(eq(1L))).thenReturn(null);
        mockMvc.perform(delete("/rest/planeTickets/1"))
        		.andDo(print())
                .andExpect(status().isNotFound());
        
    }
    @Test 
    public void updateExistingPlaneTicket() throws Exception{
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");
        ticket1.setFlightTo("Warsaw");
        ticket1.setFlightNumber("345FT");
        
        when(service.updatePlaneTicket(eq(1L), any(PlaneTicket.class))).thenReturn(ticket1);
        mockMvc.perform(put("/rest/planeTickets/1")
        		.content("{\"flightNumber\":\"345FT\",\"flightFrom\":\"Berlin\",\"flightTo\":\"Warsaw\"}")
                .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.flightNumber", is(ticket1.getFlightNumber())))
		        .andExpect(jsonPath("$.flightFrom", is(ticket1.getFlightFrom())))
		        .andExpect(jsonPath("$.flightTo", is(ticket1.getFlightTo())))
        		.andExpect(jsonPath("$.links[*].href",
        				hasItem(endsWith("/planeTickets/1"))))
        		.andDo(print())
        		.andExpect(status().isOk());
        
    }
    @Test 
    public void updateNotExistingPlaneTicket() throws Exception{
        
        when(service.updatePlaneTicket(eq(1L), any(PlaneTicket.class))).thenReturn(null);
        mockMvc.perform(put("/rest/planeTickets/1")
        		.content("{\"flightNumber\":\"345FT\",\"flightFrom\":\"Berlin\",\"flightTo\":\"Warsaw\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
        		.andExpect(status().isNotFound());
        
    }
}
