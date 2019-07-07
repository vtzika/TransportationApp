package com.transportation.app;


import java.util.List;
import java.util.ArrayList; 

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
  
    @RequestMapping("/journey")
    public Iterable<Journey> journey(@RequestParam(value="arrival", defaultValue="") String arrival, @RequestParam(value="destination", defaultValue="") String destination) {
    	System.out.println("HERE IT IS" + arrival);
    	return journeyService.findByLocations(arrival, destination);
    }
  
}
