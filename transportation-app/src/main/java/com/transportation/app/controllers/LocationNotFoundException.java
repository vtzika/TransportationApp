package com.transportation.app;

public class LocationNotFoundException extends RuntimeException {


	LocationNotFoundException(Long id) {
	    super("Could not find location " + id);
	  }
}
