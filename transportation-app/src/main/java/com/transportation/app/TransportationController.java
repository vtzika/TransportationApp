package com.transportation.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class TransportationController {

	@RequestMapping("/")
	String home() {
        System.out.println("Let's inspect the beans provided by Spring Boot:");

		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(TransportationController.class, args);
	}

	
}