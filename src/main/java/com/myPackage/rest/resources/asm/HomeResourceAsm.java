package com.myPackage.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.myPackage.core.entities.Home;
import com.myPackage.rest.mvc.HomeController;
import com.myPackage.rest.resources.HomeResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;

public class HomeResourceAsm extends ResourceAssemblerSupport<Home, HomeResource>{

	public HomeResourceAsm() {
		super(HomeController.class, HomeResource.class);
	}

	@Override
	public HomeResource toResource(Home home) {
		HomeResource resource = new HomeResource();
		resource.setTitle(home.getTitle());
//		Link link = linkTo(methodOn(HomeController.class).getHome(home.getId())).withSelfRel();
		Link link = linkTo(HomeController.class).slash(home.getId()).withSelfRel();

		resource.add(link);
		return resource;
	}

}
