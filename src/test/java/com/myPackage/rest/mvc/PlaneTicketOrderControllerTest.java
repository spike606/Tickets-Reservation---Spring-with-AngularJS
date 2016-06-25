package com.myPackage.rest.mvc;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.exceptions.PlaneTicketOrderAlreadyExistsException;
import com.myPackage.core.services.exceptions.PlaneTicketOrderNotFoundException;
import com.myPackage.core.services.util.PlaneTicketOrderList;

public class PlaneTicketOrderControllerTest {

	@InjectMocks
	private PlaneTicketOrderController controller;
	
	@Mock
	private PlaneTicketOrderService service;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
    @Test
    public void findAllPlaneTicketOrders() throws Exception {
        List<PlaneTicketOrder> list = new ArrayList<PlaneTicketOrder>();

        
        PlaneTicket ticket1 = new PlaneTicket();
        ticket1.setId(1L);
        ticket1.setFlightFrom("Berlin");     

        Account ownerTicketOrder1 = new Account();
        ownerTicketOrder1.setId(1L);
        ownerTicketOrder1.setPassword("testpassword");
        ownerTicketOrder1.setFirstname("johnny");
        ownerTicketOrder1.setLastname("bravo");
        
        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
        ticketOrder1.setPlaneTicket(ticket1);
        ticketOrder1.setOwner(ownerTicketOrder1);

        
        
        PlaneTicket ticket2 = new PlaneTicket();
        ticket2.setId(2L);
        ticket2.setFlightFrom("Warsaw");

        
        PlaneTicketOrder ticketOrder2 = new PlaneTicketOrder();
        ticketOrder2.setPlaneTicket(ticket2);
        ticketOrder2.setFirstname("john");
        ticketOrder2.setLastname("smith");

        list.add(ticketOrder1);
        list.add(ticketOrder2);
        PlaneTicketOrderList allTicketOrders = new PlaneTicketOrderList();
        allTicketOrders.setPlaneTicketOrders(list);

        when(service.findAllPlaneTicketOrders()).thenReturn(allTicketOrders);

        mockMvc.perform(get("/rest/planeTicketOrders"))
				.andDo(print())
                .andExpect(jsonPath("$.planeTicketOrders[*].firstname",
                        hasItems(endsWith("john"))))
                .andExpect(jsonPath("$.planeTicketOrders[*].lastname",
                        hasItems(endsWith("smith"))))
//                .andExpect(jsonPath("$.planeTicketOrders[0].owner.firstname",
//                		is(ownerTicketOrder1.getFirstname())))
//                .andExpect(jsonPath("$.planeTicketOrders[0].owner.lastname",
//                		is(ownerTicketOrder1.getLastname())))
//                .andExpect(jsonPath("$.planeTicketOrders[*].planeTicket.flightFrom",
//                        hasItems(endsWith("Berlin"))))
                .andExpect(status().isOk());
    }
//    @Test
//    public void getExistingPlaneTicketOrder() throws Exception {
//        PlaneTicket ticket1 = new PlaneTicket();
//        ticket1.setId(1L);
//        ticket1.setFlightFrom("Berlin");     
//
//        Account ownerTicketOrder1 = new Account();
//        ownerTicketOrder1.setId(1L);
//        ownerTicketOrder1.setFirstname("johnny");
//        ownerTicketOrder1.setLastname("bravo");
//        
//        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
//        ticketOrder1.setId(1L);
//        ticketOrder1.setOwner(ownerTicketOrder1);
//
//        when(service.findPlaneTicketOrder(1L)).thenReturn(ticketOrder1);
//
//        mockMvc.perform(get("/rest/planeTicketOrders/1"))
//				.andDo(print())
//                .andExpect(jsonPath("$.owner.firstname", is(ticketOrder1.getOwner().getFirstname())))
//                .andExpect(jsonPath("$.owner.lastname", is(ticketOrder1.getOwner().getLastname())))
//                .andExpect(jsonPath("$.links[*].href",
//                        hasItem(endsWith("/planeTicketOrders/1"))))
//                .andExpect(status().isOk());
//    }
    @Test
    public void getNotExistingPlaneTicketOrder() throws Exception{
        when(service.findPlaneTicketOrder(1L)).thenThrow(new PlaneTicketOrderNotFoundException());
        mockMvc.perform(get("/rest/planeTicketOrders/1"))
		.andDo(print())
        .andExpect(status().isNotFound());
    }
//    @Test 
//    public void createNotExistingPlaneTicketOrder() throws Exception{
//        
//        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
//        ticketOrder1.setId(1L);
//        ticketOrder1.setCountry("Poland");
//        ticketOrder1.setFirstname("johnny");
//        ticketOrder1.setLastname("bravo");
//        
//        when(service.createPlaneTicketOrder(any(PlaneTicketOrder.class))).thenReturn(ticketOrder1);
//        mockMvc.perform(post("/rest/planeTicketOrders")
//        		.content(ticketOrder1.toString())
//                .content("{\"country\":\"Poland\",\"firstName\":\"johnny\",\"lastName\":\"bravo\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//        		.andDo(print())
//                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/planeTicketOrders/1")))
//                .andExpect(jsonPath("$.planeTicket", is(ticketOrder1.getPlaneTicket())))
//                .andExpect(jsonPath("$.owner", is(ticketOrder1.getOwner())))
//                .andExpect(status().isCreated());
//        
//    }
//    @Test 
//    public void createExistingPlaneTicketOrder() throws Exception{
//        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
//        ticketOrder1.setId(1L);
//        ticketOrder1.setCountry("Poland");
//        ticketOrder1.setFirstname("johnny");
//        ticketOrder1.setLastname("bravo");
//        
//        when(service.createPlaneTicketOrder(any(PlaneTicketOrder.class))).thenThrow(new PlaneTicketOrderAlreadyExistsException());
//        mockMvc.perform(post("/rest/planeTicketOrders")
//                .content("{\"country\":\"Poland\",\"firstname\":\"johnny\",\"lastname\":\"bravo\",\"planeTicketId\":\"22\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//        		.andDo(print())
//                .andExpect(status().isConflict());
//        
//    }
//    @Test 
//    public void deleteExistingPlaneTicketOrder() throws Exception{
//        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
//        ticketOrder1.setId(1L);
//        ticketOrder1.setCountry("Poland");
//        ticketOrder1.setFirstname("johnny");
//        ticketOrder1.setLastname("bravo");
//        
//        when(service.deletePlaneTicketOrder(eq(1L))).thenReturn(ticketOrder1);
//        mockMvc.perform(delete("/rest/planeTicketOrders/1"))
//                .andExpect(jsonPath("$.country", is(ticketOrder1.getCountry())))
//                .andExpect(jsonPath("$.firstname", is(ticketOrder1.getFirstname())))
//                .andExpect(jsonPath("$.lastname", is(ticketOrder1.getLastname())))
//                .andExpect(jsonPath("$.links[*].href",
//                        hasItem(endsWith("/planeTicketOrders/1"))))
//        		.andDo(print())
//                .andExpect(status().isOk());
//        
//    }
    @Test 
    public void deleteNotExistingPlaneTicketOrder() throws Exception{
        
        when(service.deletePlaneTicketOrder(eq(1L))).thenThrow(new PlaneTicketOrderNotFoundException());
        mockMvc.perform(delete("/rest/planeTicketOrders/1"))
        		.andDo(print())
                .andExpect(status().isNotFound());
        
    }
}
