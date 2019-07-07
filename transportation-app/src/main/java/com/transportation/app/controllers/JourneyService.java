package com.transportation.app;


import java.util.List;
import java.util.ArrayList; 


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JourneyService {

	@Autowired
    private JourneyRepository journeyRepository;

	@Autowired
    private JourneyLineRepository lineRepository;

    public Iterable<Journey> list() {
        return journeyRepository.findAll();
    }
    
    public Iterable<JourneyLine> lines() {
        return lineRepository.findAll();
    }
    public Iterable<Journey> findByLocations(String arrival, String destination) {
        return journeyRepository.findByParams(arrival, destination);
    }

    public Iterable<Journey> save(List<Journey> journeys) {
        return journeyRepository.save(journeys);
    }

}