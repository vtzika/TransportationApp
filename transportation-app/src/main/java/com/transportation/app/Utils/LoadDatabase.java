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

			readDataFromJSON(repository);
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
				JourneyPass journeyPass = new JourneyPass();
				journeyPass.createJourneyPassFromJson(pass);
				Journey j1 = new Journey();
				j1.createJourneyFromJson(timePoint);
				repository.save(j1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
