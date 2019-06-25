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
}
