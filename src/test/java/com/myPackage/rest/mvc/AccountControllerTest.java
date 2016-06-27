package com.myPackage.rest.mvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myPackage.core.email.EmailHtmlSender;
import com.myPackage.core.email.EmailSender;
import com.myPackage.core.entities.Account;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.AccountAlreadyExistsException;
import org.thymeleaf.context.Context;


public class AccountControllerTest {

	@InjectMocks
	private AccountController controller;
	
	@Mock
	private AccountService service;
	
	private MockMvc mockMvc;
	
	private ArgumentCaptor<Account> accountCaptor;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		accountCaptor = ArgumentCaptor.forClass(Account.class);
	}
	
//	@Test
//	public void getExistingAccount() throws Exception {
//		Account account = new Account();
//		account.setId(1L);
//		account.setPassword("testpassword");
//		account.setFirstname("johnny");
//		account.setLastname("bravo");
//		
//		when(service.findAccount(1L)).thenReturn(account);	
//		
//		mockMvc.perform(get("/rest/accounts/1"))
//		        .andDo(print())
////		        .andExpect(jsonPath("$.password").doesNotExist())//should not exists
//		        .andExpect(jsonPath("$.firstname", is(account.getFirstname())))
//		        .andExpect(jsonPath("$.lastname", is(account.getLastname())))
//
//		        .andExpect(status().isOk());
//	}
    @Test
    public void getNotExistingAccount() throws Exception {
        when(service.findAccount(1L)).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(get("/rest/accounts/1"))
        		.andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
    
    @Test
    public void createAccountNotExistingLogin() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("pass");
        createdAccount.setLogin("testlogin");
        createdAccount.setFirstname("johnny");
        createdAccount.setLastname("bravo");
        createdAccount.setEmail("test@email.com");


        when(service.createAdminAccount(any(Account.class))).thenReturn(createdAccount);
        mockMvc.perform(post("/rest/accounts/newAdmin")
                .content("{\"login\":\"testlogin\",\"password\":\"pass\",\"firstname\":\"johnny\",\"lastname\":\"bravo\","
                		+ "\"secondname\":\"john\",\"email\":\"john@aol.com\",\"telephone\":\"+48 111-111-111\",\"country\":\"Poland\",\"state\":\"Lodzkie\","
                		+ "\"city\":\"Lodz\",\"street\":\"Zachodnia 12\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(jsonPath("$.login", is(createdAccount.getLogin())))
                .andExpect(jsonPath("$.firstname", is(createdAccount.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(createdAccount.getLastname())))
                .andExpect(status().isCreated());
        
//        verify(service).createAccount(any(Account.class));//check if createAccount was called
//        verify(service).createAdminAccount(accountCaptor.capture());//capture
//        
//        String password = accountCaptor.getValue().getPassword();
//        assertEquals("pass", password);//without @JsonProperty annotation in accountResource it is null
    }

    @Test
    public void createAccountExistingLogin() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("pass");
        createdAccount.setLogin("testlogin");
        createdAccount.setEmail("test@email.com");
        when(service.createAdminAccount(any(Account.class))).thenThrow(new AccountAlreadyExistsException());

      mockMvc.perform(post("/rest/accounts/newAdmin")
                .content("{\"login\":\"testlogin\",\"password\":\"pass\",\"firstname\":\"johnny\",\"lastname\":\"bravo\","
                		+ "\"secondname\":\"john\",\"email\":\"john@aol.com\",\"telephone\":\"+48 111-111-111\",\"country\":\"Poland\",\"state\":\"Lodzkie\","
                		+ "\"city\":\"Lodz\",\"street\":\"Zachodnia 12\"}")
                .contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isConflict());
    }
//    @Test
//    public void createPlaneTicketOrderExistingAccount() throws Exception {
//        PlaneTicketOrder createdPlaneTicketOrder = new PlaneTicketOrder();
//        createdPlaneTicketOrder.setId(1L);
//        createdPlaneTicketOrder.setFirstname("johnny");
//
//        when(service.createPlaneTicketOrderForAccount(eq(1L), any(PlaneTicketOrder.class))).thenReturn(createdPlaneTicketOrder);
//
//        mockMvc.perform(post("/rest/accounts/1/planeTicketOrders")
//                .content("{\"firstname\":\"johnny\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.firstname", is("johnny")))
//                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/planeTicketOrders/1"))))
//                .andExpect(header().string("Location", endsWith("/planeTicketOrders/1")))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void createPlaneTicketOrderNotExistingAccount() throws Exception {
//        when(service.createPlaneTicketOrderForAccount(eq(1L), any(PlaneTicketOrder.class))).thenThrow(new AccountDoesNotExistException());
//
//        mockMvc.perform(post("/rest/accounts/1/planeTicketOrders")
//                .content("{\"firstname\":\"johnny\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//    @Test
//    public void createPlaneTicketOrderExistingPlaneTicketOrder() throws Exception {
//        when(service.createPlaneTicketOrderForAccount(eq(1L), any(PlaneTicketOrder.class))).thenThrow(new PlaneTicketOrderAlreadyExistsException());
//
//        mockMvc.perform(post("/rest/accounts/1/planeTicketOrders")
//                .content("{\"firstname\":\"johnny\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isConflict());
//    }
//    @Test
//    public void findAllPlaneTicketOrdersForExistingAccount() throws Exception {
//        List<PlaneTicketOrder> list = new ArrayList<PlaneTicketOrder>();
//
//        
//        PlaneTicket ticket1 = new PlaneTicket();
//        ticket1.setId(1L);
//        ticket1.setFlightFrom("Berlin");     
//
//        Account ownerTicketOrder1 = new Account();
//        ownerTicketOrder1.setId(1L);
//        ownerTicketOrder1.setPassword("testpassword");
//        ownerTicketOrder1.setFirstname("johnny");
//        ownerTicketOrder1.setLastname("bravo");
//        
//        PlaneTicketOrder ticketOrder1 = new PlaneTicketOrder();
//        ticketOrder1.setPlaneTicket(ticket1);
//        ticketOrder1.setOwner(ownerTicketOrder1);
//
//        
//        
//        PlaneTicket ticket2 = new PlaneTicket();
//        ticket2.setId(2L);
//        ticket2.setFlightFrom("Warsaw");
//
//        
//        PlaneTicketOrder ticketOrder2 = new PlaneTicketOrder();
//        ticketOrder2.setPlaneTicket(ticket2);
//        ticketOrder2.setFirstname("john");
//        ticketOrder2.setLastname("smith");
//
//        list.add(ticketOrder1);
//        list.add(ticketOrder2);
//        PlaneTicketOrderList allTicketOrders = new PlaneTicketOrderList();
//        allTicketOrders.setPlaneTicketOrders(list);
//
//        when(service.findAllPlaneTicketOrdersForAccount(1L)).thenReturn(allTicketOrders);
//
//        mockMvc.perform(get("/rest/accounts/1/planeTicketOrders"))
//				.andDo(print())
//                .andExpect(jsonPath("$.planeTicketOrders[*].firstname",
//                        hasItems(endsWith("john"))))
//                .andExpect(jsonPath("$.planeTicketOrders[*].lastname",
//                        hasItems(endsWith("smith"))))
//                .andExpect(jsonPath("$.planeTicketOrders[0].owner.firstname",
//                		is(ownerTicketOrder1.getFirstname())))
//                .andExpect(jsonPath("$.planeTicketOrders[0].owner.lastname",
//                		is(ownerTicketOrder1.getLastname())))
//                .andExpect(jsonPath("$.planeTicketOrders[*].planeTicket.flightFrom",
//                        hasItems(endsWith("Berlin"),endsWith("Warsaw"))))
//                .andExpect(status().isOk());
//    }
    @Test
    public void findAllPlaneTicketOrdersForNotExistingAccount() throws Exception {
        when(service.findAllPlaneTicketOrdersForAccount(eq(1L))).thenThrow(new AccountDoesNotExistException());


        mockMvc.perform(get("/rest/accounts/1/planeTicketOrders"))
				.andDo(print())
                .andExpect(status().isNotFound());
    }
}
