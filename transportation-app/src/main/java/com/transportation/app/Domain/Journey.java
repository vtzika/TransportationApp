package com.transportation.app;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Journey {
	@Id @GeneratedValue
	private Long id;
	private String type;
	private double latitude;
	private double longitude;
	// private string departure_time;
	// private string duration;
	private String departure_stop_name;
	private String arrival_stop_name;
//
//	Journey() {
//	}
//
//	public Journey(String type, double latitude, double longitude, String departure_stop_name,
//			String arrival_stop_name) {
//		this.type = type;
//		this.latitude = latitude;
//		this.longitude = longitude;
//		this.departure_stop_name = departure_stop_name;
//		this.arrival_stop_name = arrival_stop_name;
//	}
}
