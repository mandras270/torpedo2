package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.ai.RandomPositionGenerator;

@Configuration
public class AppConfigRandomPositionGenerator {

	@Bean
	RandomPositionGenerator randomPositionGenerator() {
		return new RandomPositionGenerator();
	}

}
