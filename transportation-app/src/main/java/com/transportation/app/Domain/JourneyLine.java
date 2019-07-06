package com.transportation.app;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Setter
@Getter
@Entity
public class JourneyLine {
	@Id @GeneratedValue
	private Long id;
	private String code;
	private String destinationName;	
	
}
