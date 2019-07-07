package com.transportation.app;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;  

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/journeys")
public class JourneyController {
	
    private JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/list")
    public Iterable<Journey> list() {
        return journeyService.list();
    }
  
    @RequestMapping(value = "/journey", method = RequestMethod.POST)
    public Iterable<Journey> journey(@RequestParam(value="arrival", defaultValue="") String arrival, @RequestParam(value="destination", defaultValue="") String destination, @RequestBody Map<String, String> body) {
    	if(arrival.equals("")) {
        	arrival = body.get("arrival");
        	destination = body.get("destination");
    	}
    	return journeyService.findByLocations(arrival, destination);
    }
  
}
