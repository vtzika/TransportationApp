package com.transportation.app;

import lombok.Setter;
import lombok.Getter;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.json.simple.JSONObject;

@Setter
@Getter
@Entity
public class Journey {
	@Id
	@GeneratedValue
	private Long id;
	private String lineName;
	private long lineNumber;
	private String type;
//	private double latitude;
//	private double longitude;
	private String departure_time;
	private String arrival_time;
	private String arrival;
	private String destination;

	public Journey createJourneyFromJson(JSONObject journey) {

		System.out.println(journey);

		JSONObject stop = (JSONObject) journey.get("Stop");
		System.out.println("stop: " + stop);

//		Double latitude = (Double) stop.get("Latitude");
//		System.out.println("latitude: " + latitude);
//		this.latitude = latitude;
//
//		Double longitude = (Double) stop.get("Longitude");
//		System.out.println("Longitude: " + longitude);
//		this.longitude = longitude;

		String departure_stop_name = (String) stop.get("TimingPointName");
		System.out.println("Departure_stop_name: " + departure_stop_name);
		this.destination = departure_stop_name;

		JSONObject passes = (JSONObject) journey.get("Passes");
		System.out.println("PASSES: " + passes);
		Set<String> keys = passes.keySet();

		for (String key : keys) {
			JSONObject pass = (JSONObject) passes.get(key);
			this.arrival = (String) pass.get("DestinationName50");
			this.type = (String) pass.get("TransportType");
			this.departure_time = (String) pass.get("ExpectedDepartureTime");
			this.arrival_time = (String) pass.get("ExpectedArrivalTime");
			this.lineName = (String) pass.get("LineName");
			this.lineNumber = (long) pass.get("LineDirection");
		}

		return this;
	}

}
