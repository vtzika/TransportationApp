package com.transportation.app;


import java.util.List;
import java.util.ArrayList; 

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/journeys")
public class JourneyController {
	
    private JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping("/list")
    public Iterable<Journey> list() {
        return journeyService.list();
    }
  
}