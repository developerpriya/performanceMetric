package com.metric.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetricDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricDataApplication.class, args);

	}


}
