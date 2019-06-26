package com.transportation.app;

import java.util.List;
import java.util.ArrayList; 

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@EnableAutoConfiguration
public class TransportationController {

	TransportationController() {
	}


	@GetMapping("/locations")
	List<Location> all() {
		List<Location> locations = new ArrayList<Location>();
		Location l1 = new Location("ABN HQ", 40.366633, 74.640832);
		Location l2 = new Location("ABN FOPP", 40.366633, 74.640832);
		locations.add(l1);
		locations.add(l2);
		return locations;
	}


	@RequestMapping("/")
	String home() {

		return "Hello World!";
	}


}