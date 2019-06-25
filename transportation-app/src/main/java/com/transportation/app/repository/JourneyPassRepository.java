package com.transportation.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
interface JourneyPassRepository extends CrudRepository<JourneyPass, Long> {

	List<JourneyPass> findAll(); 
	List<JourneyPass> save(List<Journey> journeys); 
}

