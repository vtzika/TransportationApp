package com.transportation.app;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
		};
	}
}


