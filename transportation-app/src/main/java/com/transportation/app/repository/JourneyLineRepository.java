package com.transportation.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
interface JourneyLineRepository extends CrudRepository<JourneyLine, Long> {

	List<JourneyLine> findAll(); 
	List<JourneyLine> save(List<JourneyLine> lines); 
}

