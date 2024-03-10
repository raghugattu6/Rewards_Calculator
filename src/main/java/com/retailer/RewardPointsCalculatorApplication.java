package com.retailer;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RewardPointsCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardPointsCalculatorApplication.class, args);
		System.out.println("Current Timestamp: " + Timestamp.from(Instant.now()));
	}

}
