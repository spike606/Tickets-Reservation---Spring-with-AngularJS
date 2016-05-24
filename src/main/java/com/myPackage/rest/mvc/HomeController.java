package com.myPackage.rest.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.Home;
import com.myPackage.core.services.HomeService;
import com.myPackage.rest.resources.HomeResource;
import com.myPackage.rest.resources.asm.HomeResourceAsm;


@RestController
@RequestMapping(value = "/rest/home")
public class HomeController {

	private HomeService service;

	public HomeController() {
	}
	
	public HomeController(HomeService service) {
		this.service = service;
	}

	@RequestMapping(value = "/{homeId}", method = RequestMethod.GET)
	public ResponseEntity<HomeResource> getHome(@PathVariable Long homeId) {
		Home home = service.find(homeId);
		if (home != null) {
			HomeResource resource = new HomeResourceAsm().toResource(home);
			return new ResponseEntity<HomeResource>(resource, HttpStatus.OK);

		}else{
			return new ResponseEntity<HomeResource>( HttpStatus.NOT_FOUND);

		}
	}
	
	@RequestMapping(value = "/{homeId}", method = RequestMethod.DELETE)
	public ResponseEntity<HomeResource> deleteHome(@PathVariable Long homeId) {
		Home home = service.delete(homeId);
		if (home != null) {
			HomeResource resource = new HomeResourceAsm().toResource(home);
			return new ResponseEntity<HomeResource>(resource, HttpStatus.OK);

		}else{
			return new ResponseEntity<HomeResource>( HttpStatus.NOT_FOUND);

		}
	}
	@RequestMapping(value = "/{homeId}", method = RequestMethod.PUT)
	public ResponseEntity<HomeResource> updateHome(@PathVariable Long homeId,
			@RequestBody HomeResource sentHome) {
		Home home = service.update(homeId, sentHome.toHome());
		if (home != null) {
			HomeResource resource = new HomeResourceAsm().toResource(home);
			return new ResponseEntity<HomeResource>(resource, HttpStatus.OK);

		}else{
			return new ResponseEntity<HomeResource>( HttpStatus.NOT_FOUND);

		}
	}


}
