package com.epam.training.torpedo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.network.Server;

@Configuration
public class AppConfigLogger {

	@Bean
	Logger serverLogger() {
		Logger logger = LoggerFactory.getLogger(Server.class);
		return logger;
	}

}
