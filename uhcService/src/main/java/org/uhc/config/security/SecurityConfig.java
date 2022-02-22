/* 
 * ===========================================================================
 * File Name SecurityConfig.java
 * 
 * Created on May 25, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: SecurityConfig.java,v $
 * ===========================================================================
 */
package org.uhc.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author nehas3
 * @date May 25, 2018 Description : This is the SecurityConfig to customize the
 *       security configuration for the requested service by overriding
 *       configure method of WebSecurityConfigurerAdapter.
 */
@Configuration

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BasicAuthEntryPoint basicAuthEntryPoint;

	@Value("${security.user.name}")
	private String userName;

	@Value("${security.user.password}")
	private String password;

	/**
	 * @author nehas3
	 * @date May 25, 2018
	 * @return void
	 * @param http
	 * @throws Exception Description : This is the configure method to authenticate
	 *                   user and make sure the user is authorized to access the
	 *                   requested service.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/user/*").hasAnyRole("ADMIN", "USER")
		.antMatchers("/user/**").authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().httpBasic().realmName("UHC_SERVICE_REALM")
		.authenticationEntryPoint(basicAuthEntryPoint);
	}

	/**
	 * 
	 * @author nehas3
	 * @date May 25, 2018
	 * @return void
	 * @param auth Description
	 */

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(userName).password(password).roles("USER");
	}
}
