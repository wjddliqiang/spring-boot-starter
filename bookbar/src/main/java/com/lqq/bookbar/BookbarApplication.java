package com.lqq.bookbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.lqq.bookbar.controller.Person;
 
@SpringBootApplication
@EnableConfigurationProperties({Person.class})
public class BookbarApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookbarApplication.class, args);
		
	}
	
	
	

}
