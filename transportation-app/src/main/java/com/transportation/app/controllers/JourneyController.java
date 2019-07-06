package com.transportation.app;


import java.util.List;
import java.util.ArrayList; 

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/journeys")
public class JourneyController {
	
    private JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }
    
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/list")
    public Iterable<Journey> list() {
        return journeyService.list();
    }
  
}
