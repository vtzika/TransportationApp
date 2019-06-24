package com.transportation.app;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Location {
	private @Id @GeneratedValue Long id;
    private String name;
	private double latitude;
	private double longitude;

	  Location() {}

	  
	    public Location(String name, double latitude, double longitude) {
	        this.name = name;
	        this.latitude  = latitude;
	        this.longitude = longitude;
	    }
}
