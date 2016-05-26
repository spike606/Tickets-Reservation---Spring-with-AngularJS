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
import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.exceptions.TrainTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.TrainTicketOrderList;

public class TrainTicketOrderControllerTest {

	@InjectMocks
	private TrainTicketOrderController controller;
	
	@Mock
	private TrainTicketOrderService service;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
    @Test
    public void findAllTrainTicketOrders() throws Exception {
        List<TrainTicketOrder> list = new ArrayList<TrainTicketOrder>();

        
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");     

        Account ownerTicketOrder1 = new Account();
        ownerTicketOrder1.setId(1L);
        ownerTicketOrder1.setPassword("testpassword");
        ownerTicketOrder1.setFirstname("johnny");
        ownerTicketOrder1.setLastname("bravo");
        
        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
        ticketOrder1.setTrainTicket(ticket1);
        ticketOrder1.setOwner(ownerTicketOrder1);

        
        
        TrainTicket ticket2 = new TrainTicket();
        ticket2.setId(2L);
        ticket2.setTransitFrom("Warsaw");

        
        TrainTicketOrder ticketOrder2 = new TrainTicketOrder();
        ticketOrder2.setTrainTicket(ticket2);
        ticketOrder2.setFirstname("john");
        ticketOrder2.setLastname("smith");

        list.add(ticketOrder1);
        list.add(ticketOrder2);
        TrainTicketOrderList allTicketOrders = new TrainTicketOrderList();
        allTicketOrders.setTrainTicketOrders(list);

        when(service.findAllTrainTicketOrders()).thenReturn(allTicketOrders);

        mockMvc.perform(get("/rest/trainTicketOrders"))
				.andDo(print())
                .andExpect(jsonPath("$.trainTicketOrders[*].firstname",
                        hasItems(endsWith("john"))))
                .andExpect(jsonPath("$.trainTicketOrders[*].lastname",
                        hasItems(endsWith("smith"))))
                .andExpect(jsonPath("$.trainTicketOrders[0].owner.firstname",
                		is(ownerTicketOrder1.getFirstname())))
                .andExpect(jsonPath("$.trainTicketOrders[0].owner.lastname",
                		is(ownerTicketOrder1.getLastname())))
                .andExpect(jsonPath("$.trainTicketOrders[*].trainTicket.transitFrom",
                        hasItems(endsWith("Berlin"),endsWith("Warsaw"))))
                .andExpect(status().isOk());
    }
    @Test
    public void getExistingTrainTicketOrder() throws Exception {
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");     

        Account ownerTicketOrder1 = new Account();
        ownerTicketOrder1.setId(1L);
        ownerTicketOrder1.setFirstname("johnny");
        ownerTicketOrder1.setLastname("bravo");
        
        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
        ticketOrder1.setId(1L);
        ticketOrder1.setTrainTicket(ticket1);
        ticketOrder1.setOwner(ownerTicketOrder1);

        when(service.findTrainTicketOrder(1L)).thenReturn(ticketOrder1);

        mockMvc.perform(get("/rest/trainTicketOrders/1"))
				.andDo(print())
                .andExpect(jsonPath("$.owner.firstname", is(ticketOrder1.getOwner().getFirstname())))
                .andExpect(jsonPath("$.owner.lastname", is(ticketOrder1.getOwner().getLastname())))
                .andExpect(jsonPath("$.trainTicket.transitFrom", is(ticketOrder1.getTrainTicket().getTransitFrom())))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/trainTicketOrders/1"))))
                .andExpect(status().isOk());
    }
    @Test
    public void getNotExistingTrainTicketOrder() throws Exception{
        when(service.findTrainTicketOrder(1L)).thenReturn(null);
        mockMvc.perform(get("/rest/trainTicketOrders/1"))
		.andDo(print())
        .andExpect(status().isNotFound());
    }
    @Test 
    public void createNotExistingTrainTicketOrder() throws Exception{
        
        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
        ticketOrder1.setId(1L);
        ticketOrder1.setCountry("Poland");
        ticketOrder1.setFirstname("johnny");
        ticketOrder1.setLastname("bravo");
        
        when(service.createTrainTicketOrder(any(TrainTicketOrder.class))).thenReturn(ticketOrder1);
        mockMvc.perform(post("/rest/trainTicketOrders")
        		.content(ticketOrder1.toString())
                .content("{\"country\":\"Poland\",\"firstName\":\"johnny\",\"lastName\":\"bravo\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/trainTicketOrders/1")))
                .andExpect(jsonPath("$.trainTicket", is(ticketOrder1.getTrainTicket())))
                .andExpect(jsonPath("$.owner", is(ticketOrder1.getOwner())))
                .andExpect(status().isCreated());
        
    }
    @Test 
    public void createExistingTrainTicketOrder() throws Exception{
        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
        ticketOrder1.setId(1L);
        ticketOrder1.setCountry("Poland");
        ticketOrder1.setFirstname("johnny");
        ticketOrder1.setLastname("bravo");
        
        when(service.createTrainTicketOrder(any(TrainTicketOrder.class))).thenThrow(new TrainTicketOrderAlreadyExistsException());
        mockMvc.perform(post("/rest/trainTicketOrders")
                .content("{\"country\":\"Poland\",\"firstName\":\"johnny\",\"lastName\":\"bravo\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isConflict());
        
    }
    @Test 
    public void deleteExistingTrainTicketOrder() throws Exception{
        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
        ticketOrder1.setId(1L);
        ticketOrder1.setCountry("Poland");
        ticketOrder1.setFirstname("johnny");
        ticketOrder1.setLastname("bravo");
        
        when(service.deleteTrainTicketOrder(eq(1L))).thenReturn(ticketOrder1);
        mockMvc.perform(delete("/rest/trainTicketOrders/1"))
                .andExpect(jsonPath("$.country", is(ticketOrder1.getCountry())))
                .andExpect(jsonPath("$.firstname", is(ticketOrder1.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(ticketOrder1.getLastname())))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/trainTicketOrders/1"))))
        		.andDo(print())
                .andExpect(status().isOk());
        
    }
    @Test 
    public void deleteNotExistingTrainTicketOrder() throws Exception{
        
        when(service.deleteTrainTicketOrder(eq(1L))).thenReturn(null);
        mockMvc.perform(delete("/rest/trainTicketOrders/1"))
        		.andDo(print())
                .andExpect(status().isNotFound());
        
    }
//    @Test 
//    public void updateExistingTrainTicketOrder() throws Exception{
//        TrainTicketOrder ticketOrder1 = new TrainTicketOrder();
//        ticketOrder1.setId(1L);
//        ticketOrder1.setCountry("Poland");
//        ticketOrder1.setFirstname("johnny");
//        ticketOrder1.setLastname("bravo");
//        
//        when(service.updateTrainTicketOrder(eq(1L), any(TrainTicketOrder.class))).thenReturn(ticketOrder1);
//        mockMvc.perform(put("/rest/trainTicketOrders/1")
//                .content("{\"country\":\"Poland\",\"firstName\":\"johnny\",\"lastName\":\"bravo\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//		        .andExpect(jsonPath("$.country", is(ticketOrder1.getCountry())))
//		        .andExpect(jsonPath("$.firstname", is(ticketOrder1.getFirstname())))
//		        .andExpect(jsonPath("$.lastname", is(ticketOrder1.getLastname())))
//        		.andExpect(jsonPath("$.links[*].href",
//        				hasItem(endsWith("/trainTicketOrders/1"))))
//        		.andDo(print())
//        		.andExpect(status().isOk());
//        
//    }
//    @Test 
//    public void updateNotExistingTrainTicket() throws Exception{
//        
//        when(service.updateTrainTicketOrder(eq(1L), any(TrainTicketOrder.class))).thenReturn(null);
//        mockMvc.perform(put("/rest/trainTicketOrders/1")
//                .content("{\"country\":\"Poland\",\"firstName\":\"johnny\",\"lastName\":\"bravo\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//        		.andDo(print())
//        		.andExpect(status().isNotFound());
//        
//    }
	
}
