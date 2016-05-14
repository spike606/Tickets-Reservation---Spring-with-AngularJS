package com.myPackage.core.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


public class HomeControllerTest {

	
	@InjectMocks
	private HomeController controller;
	
	private MockMvc mockMvc;
	
	
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);//inject controller
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
			
		
		
	}
	
	@Test
	public void test() throws Exception{
		
//		mockMvc.perform(get("/home")).andDo(print());
        mockMvc.perform(post("/home")
                .content("{\"title\":\"my title\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.title", is("my title")))
                .andDo(print());

		
		
	}
	
	
	
	
}
