package com.transportation.app;


import java.util.List;
import java.util.ArrayList; 


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JourneyService {

	@Autowired
    private JourneyRepository journeyRepository;


    public Iterable<Journey> list() {
        return journeyRepository.findAll();
    }

    public Iterable<Journey> save(List<Journey> journeys) {
        return journeyRepository.save(journeys);
    }

}