package com.transportation.app;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/{transportation}")
public class TransportationResources {
	
	/*
	@RequestMapping(value="/{abnlocations}", method=RequestMethod.GET)
	public Location getLocations(@PathVariable Long location) {
		return new Location("a", "b");
	}

	@GetMapping("/location/{latitude}/{longtitude}")
	 Location one(@PathVariable String lat, @PathVariable String longt) {
		return new Location(lat, longt);
	   
	  }

	@GetMapping("/locations")
	 Location getAll() {
		return new Location("a", "b");
	  }
*/
}
