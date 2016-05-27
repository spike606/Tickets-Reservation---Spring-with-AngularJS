package com.myPackage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class TicketsServiceApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      	registry.addResourceHandler("/**").addResourceLocations(
				"classpath:/static/ngbp/build/");
    }
	
	public static void main(String[] args) {
		SpringApplication.run(TicketsServiceApplication.class, args);
	}
}
