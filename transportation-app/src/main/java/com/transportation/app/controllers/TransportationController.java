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

	/*@Autowired
	private final LocationRepository repository;
*/
	TransportationController() {
		//this.repository = repository;
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

	//based 2 locations I want to return next journeys
//	@GetMapping("/test-journeys/")
//	List<Journey> nextJourneys() {
//		List<Journey> journeys = new ArrayList<Journey>();
//		Journey j1 = new Journey("BUS", 51.658607, 5.371466, "Den Dungen, Dungens Molen",
//				"Den Bosch");
//		journeys.add(j1);
//		return journeys;
//		}
	
	//http://v0.ovapi.nl/tpc/63150020,63150050
	

	@RequestMapping("/")
	String home() {

		return "Hello World!";
	}


}