package com.transportation.app;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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

			System.out.println("loading journey data...");
			List<Journey> journeys = new ArrayList<Journey>();
			Journey j1 = new Journey();
			j1.setType("BUS");
			j1.setLatitude(51.658607);
			j1.setLongitude(51.658607);
			j1.setDeparture_stop_name("Den Dungen, Dungens Molen");
			j1.setArrival_stop_name("Den Bosch");
			repository.save(j1);

			callTimepointAPI(repository);
			// readDataFromJSON(repository);
		};
	}

	public void readDataFromJSON(JourneyRepository repository) {
		JSONParser parser = new JSONParser();
		try {

			File myFile = new File("testData.json");
			System.out.println("Attempting to read from file in: " + myFile.getCanonicalPath());
			Object obj = parser.parse(new FileReader(myFile.getCanonicalPath()));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject timePoint = (JSONObject) jsonObject.get("63150050");
			JSONObject passes = (JSONObject) timePoint.get("Passes");

			for (Iterator iterator = passes.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				JSONObject pass = (JSONObject) passes.get(key);
				createJourneyFromJson(timePoint, pass, repository);
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
		String resourceUrl = "http://v0.ovapi.nl/tpc/";
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
}
