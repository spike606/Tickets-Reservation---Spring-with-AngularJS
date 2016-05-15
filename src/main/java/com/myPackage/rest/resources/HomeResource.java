package com.myPackage.rest.resources;

import org.springframework.hateoas.ResourceSupport;

public class HomeResource extends ResourceSupport {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
