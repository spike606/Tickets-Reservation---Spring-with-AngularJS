package com.myPackage.rest.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myPackage.core.entities.Home;
import com.myPackage.core.services.HomeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class HomeControllerTest {

	
	@InjectMocks
	private HomeController controller;
	
	@Mock
	private HomeService service;
	
	private MockMvc mockMvc;
	
	
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);//inject controller
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
			
		
		
	}
	
	@Test
	public void getExistingHome() throws Exception{
		Home home = new Home();
		home.setTitle("test title");
		home.setId(1L);
		
		when(service.find(1L)).thenReturn(home);
		
		mockMvc.perform(get("/rest/home/1"))
		.andDo(print())
			.andExpect(jsonPath("$.title", is(home.getTitle())))
			.andExpect(jsonPath("$.links[*].href",
                  hasItems(endsWith("/home/1"))))
			.andExpect(status().isOk());
		
	}
	@Test
	public void getNonExistingHome() throws Exception{
		
		when(service.find(1L)).thenReturn(null);
		
		mockMvc.perform(get("/rest/home/1"))
			.andExpect(status().isNotFound());
		
	}
	
	
	
}
