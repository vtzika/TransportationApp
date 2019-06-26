package com.transportation.app;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.json.simple.JSONObject;

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
	
	public Journey createJourneyFromJson(JSONObject journey) {
		
		System.out.println(journey);

		JSONObject stop = (JSONObject) journey.get("Stop");
		System.out.println("stop: " + stop);
		
		Double latitude = (Double) stop.get("Latitude");
		System.out.println("latitude: " + latitude);
		this.latitude = latitude;

		Double longitude = (Double) stop.get("Longitude");
		System.out.println("Longitude: " + longitude);
		this.longitude = longitude;
		
		String departure_stop_name = (String) stop.get("TimingPointName");
		System.out.println("Departure_stop_name: " + departure_stop_name);
		this.departure_stop_name = departure_stop_name;
		
		this.type = "type TODO";
		this.arrival_stop_name = "ARRIVAL TODO";
		
		return this;
	}
	
	
	
}
