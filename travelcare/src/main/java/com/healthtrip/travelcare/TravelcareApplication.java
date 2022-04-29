package com.healthtrip.travelcare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
public class TravelcareApplication {

	public static void main(String[] args) {
		log.info("-------------"+System.getenv("DBurl")+":5432/postgres");
		SpringApplication.run(TravelcareApplication.class, args);
	}

}
