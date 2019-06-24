package com.transportation.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
interface JourneyRepository extends CrudRepository<Journey, Long> {

	List<Journey> findAll(); 
	List<Journey> save(List<Journey> journeys); 
}

