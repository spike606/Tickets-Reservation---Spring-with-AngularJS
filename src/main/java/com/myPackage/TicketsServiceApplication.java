package com.myPackage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.myPackage.core.entities.Account;

@SpringBootApplication
public class TicketsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsServiceApplication.class, args);
	}
}
