package com.transportation.app;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.Line;

import java.io.InputStream;
import java.io.IOException;
import java.lang.Exception;
import java.util.Iterator;
import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Configuration
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(JourneyRepository repository) {
		return args -> {

//			System.out.println("loading journey data...");
//			List<Journey> journeys = new ArrayList<Journey>();
//			Journey j1 = new Journey();
//			j1.setType("BUS");
//			j1.setLatitude(51.658607);
//			j1.setLongitude(51.658607);
//			j1.setDeparture_stop_name("Den Dungen, Dungens Molen");
//			j1.setArrival_stop_name("Den Bosch");
//			repository.save(j1);

			// callTimepointAPI(repository);
			readDataFromJSON(repository);
			//getAllLines(repository);
//			List<String> journeys = getAllJourneys();
//			int i = 0;
//			for(String journeyID: journeys) {
//				i++;
//				readAllStops(journeyID);
//				if (i == 8)
//				{
//					return;
//				}
//			}
		};
	}

	public void readDataFromJSON(JourneyRepository repository) {
		JSONParser parser = new JSONParser();
		try {

			File myFile = new File("testData.json");
			System.out.println("Attempting to read from file in: " + myFile.getCanonicalPath());
			Object obj = parser.parse(new FileReader(myFile.getCanonicalPath()));
			JSONObject jsonObject = (JSONObject) obj;	
			
			//-----
			Set<String> keys = jsonObject.keySet();
			for(String key: keys )
			{
				System.out.println(key);
				JSONObject timePoint = (JSONObject) jsonObject.get(key);
				JSONObject passes = (JSONObject) timePoint.get("Passes");

				for (Iterator iterator = passes.keySet().iterator(); iterator.hasNext();) {
					String passKey = (String) iterator.next();
					JSONObject pass = (JSONObject) passes.get(passKey);
					createJourneyFromJson(timePoint, pass, repository);
				}	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createJourneyFromJson(JSONObject timePoint, JSONObject pass, JourneyRepository repository) {
		JourneyPass journeyPass = new JourneyPass();
		journeyPass.createJourneyPassFromJson(pass);
		Journey j1 = new Journey();
		j1.createJourneyFromJson(timePoint);
		repository.save(j1);
	}

	public void callTimepointAPI(JourneyRepository repository) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://v0.ovapi.nl/tpc" + "/";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/63150020,63150050", String.class);
		System.out.println("Status code from TPC API call: " + response.getStatusCode());
		String body = response.getBody();
		System.out.println("Body Response: " + body);
		System.out.println("-------------");

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tpcNode = mapper.readTree(body);
			System.out.println("TPC value: " + tpcNode);
			System.out.println("-------------");
			JsonNode timePointNode = tpcNode.path("63150050");
			System.out.println("TimePointNode value: " + timePointNode);
			System.out.println("-------------");
			JsonNode pass = timePointNode.path("Passes");
			System.out.println("Passes: " + pass);
			System.out.println("-------------");
			JourneyPass journeyPass = new JourneyPass();
			journeyPass.createJourneyPassFromJsonNode(pass);
			// TODO:
//			Journey j1 = new Journey();
//			j1.createJourneyFromJsonNode(timePointNode);
			// repository.save(journeyPass);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public void getAllLines(JourneyLineRepository repository) {
		String[] stationNames = new String[10];
		stationNames[0] = "Station Bijlmer ArenA";
		// ["Station Bijlmer ArenA", "Zuid Station"];

		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://v0.ovapi.nl/line/";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		System.out.println("Status code from Line API call: " + response.getStatusCode());
		String body = response.getBody();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode lineJson = mapper.readTree(body);
			Iterator<String> fieldNames = lineJson.fieldNames();
			List<JourneyLine> lines = new ArrayList<JourneyLine>();
			while (fieldNames.hasNext()) {
				String lineCode = fieldNames.next();
				JsonNode lineNode = lineJson.path(lineCode);
				String destinationName = lineNode.path("DestinationName50").asText();
				if (destinationName.contains("Station Bijlmer ArenA")) {
					JourneyLine line = new JourneyLine();
					line.setCode(lineCode);
					line.setDestinationName(destinationName);

					lines.add(line);
				}
			}
			HashMap<JourneyLine, List<String>> tpc = getTpcFromLine(lines);
			getAllStops(tpc);
			// repository.save(lines);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public HashMap<JourneyLine, List<String>> getTpcFromLine(List<JourneyLine> lines) {
		HashMap<JourneyLine, List<String>> linesTPC = new HashMap<JourneyLine, List<String>>();

		RestTemplate restTemplate = new RestTemplate();
		for (JourneyLine line : lines) {
			List<String> timePointCodes = new ArrayList<String>();
			String resourceUrl = "http://v0.ovapi.nl/line/" + line.getCode();
			ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
			System.out.println("Status code from Line API call given line code: " + response.getStatusCode());
			String body = response.getBody();
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode lineJson = mapper.readTree(body);
				// String lineCode = fieldNames.next();
				JsonNode actualsNode = lineJson.path(line.getCode()).path("Actuals");
				Iterator<String> actuals = actualsNode.fieldNames();
				while (actuals.hasNext()) {
					String actualName = actuals.next();
					String timePointCode = actualsNode.path(actualName).path("TimingPointCode").asText();
					timePointCodes.add(timePointCode);
				}

			} catch (Exception e) {
				System.out.println("Exception: " + e);
			}
			if (timePointCodes.size() > 0) {
				linesTPC.put(line, timePointCodes);
			}
		}
		return linesTPC;

	}

	public void getAllStops(HashMap<JourneyLine, List<String>> tpcs) {
		for (Map.Entry<JourneyLine, List<String>> line : tpcs.entrySet()) {
			System.out.println(line.getKey() + " = " + line.getValue());
			for (String tpc: line.getValue()) {
				System.out.println( "TPC" + tpc);
				getStopsByTPC(tpc);
			}
			
		}

	}

	public void getStopsByTPC(String tpc) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://v0.ovapi.nl/tpc/" + tpc;
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		System.out.println("Status code from TPC API call: " + response.getStatusCode());
		String body = response.getBody();
		System.out.println("Body Response: " + body);
		System.out.println("-------------");

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tpcNode = mapper.readTree(body);
			System.out.println("TPC value: " + tpcNode);
			System.out.println("-------------");
			JsonNode timePointNode = tpcNode.path("63150050");
			System.out.println("TimePointNode value: " + timePointNode);
			System.out.println("-------------");
			JsonNode pass = timePointNode.path("Passes");
			System.out.println("Passes: " + pass);
			System.out.println("-------------");
			JourneyPass journeyPass = new JourneyPass();
			journeyPass.createJourneyPassFromJsonNode(pass);
			// TODO:
//			Journey j1 = new Journey();
//			j1.createJourneyFromJsonNode(timePointNode);
			// repository.save(journeyPass);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	public List<String> getAllJourneys() {
        List<String> journeys = new ArrayList<String>(); 

        RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://v0.ovapi.nl/journey";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		System.out.println("Status code from journey call: " + response.getStatusCode());
		String body = response.getBody();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode journeyNode = mapper.readTree(body);
			Iterator<String> journeyIterator = journeyNode.fieldNames();
			while(journeyIterator.hasNext()) {
				journeys.add(journeyIterator.next());
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return journeys;
	}
	
	public void readAllStops(String journeyID) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://v0.ovapi.nl/journey/" + journeyID;
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		System.out.println("Status code from journey call: " + response.getStatusCode());
		if(response.getStatusCode().toString().equals("200")) {
			return;
		}
		String body = response.getBody();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode parentJourneyNode = mapper.readTree(body);
			JsonNode journeyNode = parentJourneyNode.path(journeyID);

			JsonNode stops = journeyNode.path("Stops");
			Iterator<String> stopsIds = stops.fieldNames();
			while (stopsIds.hasNext()) {
				String stopId = stopsIds.next();
				String stopname = stops.path(stopId).path("TimingPointName").asText();
				System.out.println("STOPS: " + stopname);

				String transportationType = stops.path(stopId).path("TransportType").asText();
				System.out.println("transportationType: " + transportationType);
				
				String arrivalTime = stops.path(stopId).path("TargetArrivalTime").asText();
				System.out.println("TargetArrivalTime: " + arrivalTime);
				
				String departureTime = stops.path(stopId).path("TargetDepartureTime").asText();
				System.out.println("TargetDepartureTime: " + departureTime);
				
				String lineName = stops.path(stopId).path("LineName").asText();
				System.out.println("LineName: " + lineName);
				
				//TODO create Stop from details 
			}

			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
