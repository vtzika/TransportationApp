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
public class JourneyPass {
	@Id @GeneratedValue
	private Long id;
	private String passStopName;
	private String departureTime;
	private String journeyStopType;
	private String transportationType;
	
	
	public void createJourneyPassFromJson(JSONObject pass) {
		this.passStopName = (String) pass.get("TimingPointName");
		this.departureTime = (String) pass.get("ExpectedArrivalTime");
		this.journeyStopType = (String) pass.get("JourneyStopType");
		this.transportationType = (String) pass.get("TransportType");
	}
}
