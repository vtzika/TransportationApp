package com.transportation.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


@Repository
interface JourneyRepository extends CrudRepository<Journey, Long> {

	List<Journey> findAll(); 
	List<Journey> save(List<Journey> journeys);
	
  
    @Query("select j from Journey j where j.arrival = :arrival and j.destination = :destination")
    List<Journey> findByParams(@Param("arrival") String arrival, @Param("destination") String destination);
	
}

