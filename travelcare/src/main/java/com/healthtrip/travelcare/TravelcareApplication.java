package com.healthtrip.travelcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelcareApplication {

	public static void main(String[] args) {
		System.out.println("-------------"+System.getenv("DBurl")+":5432/postgres");
		SpringApplication.run(TravelcareApplication.class, args);
	}

}
