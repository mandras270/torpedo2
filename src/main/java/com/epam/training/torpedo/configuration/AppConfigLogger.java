package com.epam.training.torpedo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.network.Server;

@Configuration
public class AppConfigLogger {

	@Bean
	Logger serverLogger() {
		Logger logger = LoggerFactory.getLogger(Server.class);
		return logger;
	}

	@Bean
	Logger easyShooterLogger() {
		Logger logger = LoggerFactory.getLogger(EasyShooter.class);
		return logger;
	}

	@Bean
	Logger gameTableLogger() {
		Logger logger = LoggerFactory.getLogger(GameTable.class);
		return logger;
	}

}
