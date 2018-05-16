package com.n26.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Main class for the spring boot application, as we have spring-mvc in
 * class-path, spring boot will create a dispatcher servlet and will run this as a web application
 */

@SpringBootApplication
public class TransactionApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		SpringApplication.run(TransactionApplication.class, args);
	}
	
	/**
	 * this method is overridden to deploy this application as war in stand alone tomcat
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TransactionApplication.class);
	}
}
