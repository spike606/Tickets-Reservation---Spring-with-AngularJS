package com.myPackage.core.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.Home;

@RestController
public class HomeController {

	@RequestMapping(value="/home", method = RequestMethod.POST)
	public @ResponseBody Home home(@RequestBody Home home){
		
//		Home home = new Home();		
//		home.setTitle("my title");
		
		
		
		return home;
			
		
	}
	
}
