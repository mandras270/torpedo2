package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.ai.Shooter;

@Configuration
public class AppConfigShooter {

	@Bean
	Shooter shooter() {
		Shooter shooter = new EasyShooter();
		return shooter;
	}

}
