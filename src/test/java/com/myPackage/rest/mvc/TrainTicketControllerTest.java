package com.myPackage.rest.mvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.eq;
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

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.services.TrainTicketService;
import com.myPackage.core.services.exceptions.TrainTicketAlreadyExistsException;
import com.myPackage.core.services.exceptions.TrainTicketNotFoundException;
import com.myPackage.core.services.exceptions.TrainTicketOrderNotFoundException;
import com.myPackage.core.services.util.TrainTicketList;

public class TrainTicketControllerTest {

	@InjectMocks
	private TrainTicketController controller;
	
	@Mock
	private TrainTicketService service;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
    @Test
    public void findAllTrainTickets() throws Exception {
        List<TrainTicket> list = new ArrayList<TrainTicket>();

        
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");
        list.add(ticket1);

        TrainTicket ticket2 = new TrainTicket();
        ticket2.setId(2L);
        ticket2.setTransitFrom("Warsaw");
        list.add(ticket2);

        TrainTicketList allTickets = new TrainTicketList();
        allTickets.setTrainTickets(list);

        when(service.findAllTrainTickets()).thenReturn(allTickets);

        mockMvc.perform(get("/rest/trainTickets"))
				.andDo(print())	
                .andExpect(jsonPath("$.trainTickets[*].transitFrom",
                        hasItems(endsWith("Berlin"),endsWith("Warsaw"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getExistingTrainTicket() throws Exception {
    	TrainTicket trainTicket = new TrainTicket();
    	trainTicket.setId(1L);
    	trainTicket.setTransitFrom("Berlin");

        when(service.findTrainTicket(1L)).thenReturn(trainTicket);

        mockMvc.perform(get("/rest/trainTickets/1"))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/trainTickets/1"))))
                .andExpect(jsonPath("$.transitFrom", is(trainTicket.getTransitFrom())))
                .andExpect(status().isOk());
    }
    
    @Test
    public void getNotExistingTrainTicket() throws Exception{
        when(service.findTrainTicket(1L)).thenThrow(new TrainTicketNotFoundException());
        mockMvc.perform(get("/rest/trainTickets/1"))
		.andDo(print())
        .andExpect(status().isNotFound());
    }

    @Test 
    public void createNotExistingTrainTicket() throws Exception{
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");
        ticket1.setTransitTo("Warw");
        ticket1.setTransitNumber("345FTTT");
        
        when(service.createTrainTicket(any(TrainTicket.class))).thenReturn(ticket1);
        mockMvc.perform(post("/rest/trainTickets")
                .content("{\"transitNumber\":\"45NN\",\"transitName\":\"Capt\",\"transitFrom\":\"London\",\"transitTo\":\"Warsaw\",\"transitDateStart\":\"2016-06-06\","
                		+ "\"transitHourStart\":\"12:00\",\"transitDateStop\":\"2016-06-06\",\"transitHourStop\":\"14:00\",\"transitPrice\":\"560\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/trainTickets/1")))
                .andExpect(jsonPath("$.transitNumber", is(ticket1.getTransitNumber())))
                .andExpect(jsonPath("$.transitFrom", is(ticket1.getTransitFrom())))
                .andExpect(jsonPath("$.transitTo", is(ticket1.getTransitTo())))
                .andExpect(status().isCreated());
        
    }
    @Test 
    public void createExistingTrainTicket() throws Exception{
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");
        ticket1.setTransitTo("Warsaw");
        ticket1.setTransitNumber("345FTTT");
        
        when(service.createTrainTicket(any(TrainTicket.class))).thenThrow(new TrainTicketAlreadyExistsException());
        mockMvc.perform(post("/rest/trainTickets")
                .content("{\"transitNumber\":\"45NN\",\"transitName\":\"Capt\",\"transitFrom\":\"London\",\"transitTo\":\"Warsaw\",\"transitDateStart\":\"2016-06-06\","
                		+ "\"transitHourStart\":\"12:00\",\"transitDateStop\":\"2016-06-06\",\"transitHourStop\":\"14:00\",\"transitPrice\":\"560\"}")                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isConflict());
        
    }
//    @Test 
//    public void deleteExistingTrainTicket() throws Exception{
//        TrainTicket ticket1 = new TrainTicket();
//        ticket1.setId(1L);
//        ticket1.setTransitFrom("Berlin");
//        ticket1.setTransitTo("Warsaw");
//        ticket1.setTransitNumber("345FTTT");
//        
//        when(service.deleteTrainTicket(eq(1L))).thenReturn(ticket1);
//        mockMvc.perform(delete("/rest/trainTickets/1"))
//                .andExpect(jsonPath("$.transitFrom", is(ticket1.getTransitFrom())))
//                .andExpect(jsonPath("$.links[*].href",
//                        hasItem(endsWith("/trainTickets/1"))))
//        		.andDo(print())
//                .andExpect(status().isOk());
//        
//    }
//    @Test 
//    public void deleteNotExistingTrainTicket() throws Exception{
//        
//        when(service.deleteTrainTicket(eq(1L))).thenThrow(new TrainTicketNotFoundException());
//        mockMvc.perform(delete("/rest/trainTickets/1"))
//        		.andDo(print())
//                .andExpect(status().isNotFound());
//        
//    }
    @Test 
    public void updateExistingTrainTicket() throws Exception{
        TrainTicket ticket1 = new TrainTicket();
        ticket1.setId(1L);
        ticket1.setTransitFrom("Berlin");
        ticket1.setTransitTo("Warsaw");
        ticket1.setTransitNumber("345FT");
        
        when(service.updateTrainTicket(eq(1L), any(TrainTicket.class))).thenReturn(ticket1);
        mockMvc.perform(put("/rest/trainTickets/1")
                .content("{\"transitNumber\":\"45NN\",\"transitName\":\"Capt\",\"transitFrom\":\"London\",\"transitTo\":\"Warsaw\",\"transitDateStart\":\"2016-06-06\","
                		+ "\"transitHourStart\":\"12:00\",\"transitDateStop\":\"2016-06-06\",\"transitHourStop\":\"14:00\",\"transitPrice\":\"560\"}")
                .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.transitNumber", is(ticket1.getTransitNumber())))
		        .andExpect(jsonPath("$.transitFrom", is(ticket1.getTransitFrom())))
		        .andExpect(jsonPath("$.transitTo", is(ticket1.getTransitTo())))
        		.andExpect(jsonPath("$.links[*].href",
        				hasItem(endsWith("/trainTickets/1"))))
        		.andDo(print())
        		.andExpect(status().isOk());
        
    }
    @Test 
    public void updateNotExistingTrainTicket() throws Exception{
        
        when(service.updateTrainTicket(eq(1L), any(TrainTicket.class))).thenThrow(new TrainTicketNotFoundException());
        mockMvc.perform(put("/rest/trainTickets/1")
                .content("{\"transitNumber\":\"45NN\",\"transitName\":\"Capt\",\"transitFrom\":\"London\",\"transitTo\":\"Warsaw\",\"transitDateStart\":\"2016-06-06\","
                		+ "\"transitHourStart\":\"12:00\",\"transitDateStop\":\"2016-06-06\",\"transitHourStop\":\"14:00\",\"transitPrice\":\"560\"}")                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
        		.andExpect(status().isNotFound());
        
    }
	
}
