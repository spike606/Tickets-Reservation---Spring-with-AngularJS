package com.myPackage.core.services;

import com.myPackage.core.entities.Home;

public interface HomeService {

	
	public Home find(Long id);
	public Home delete(Long id);
	
	public Home update(Long id, Home data);
	
}
