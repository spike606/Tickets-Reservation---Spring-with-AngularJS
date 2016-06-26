package com.myPackage.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationFailure authenticationFailure;
	
	@Autowired
	private AuthenticationSuccess authenticationSuccess;
	
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception{
		builder.userDetailsService(userDetailServiceImpl);
			
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling()
			.authenticationEntryPoint(entryPointUnauthorizedHandler)
			.and()			
		.formLogin()
			.successHandler(authenticationSuccess)
			.failureHandler(authenticationFailure)
			.permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/**").permitAll()
		.antMatchers("/assets/").permitAll()
//		.antMatchers("/account/usersList.tpl.html").hasAuthority("ADMIN")
		.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/")
			.permitAll()
		;
		
	}
}
