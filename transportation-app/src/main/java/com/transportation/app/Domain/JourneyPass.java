package com.transportation.app;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;

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
	
	public void createJourneyPassFromJsonNode(JsonNode pass) {
		pass = pass.get("ARR_20190626_22156_1006_0");
		this.passStopName = (String) pass.get("TimingPointName").asText();
		this.departureTime = (String) pass.get("ExpectedArrivalTime").asText();
		this.journeyStopType = (String) pass.get("JourneyStopType").asText();
		this.transportationType = (String) pass.get("TransportType").asText();
	}
}
