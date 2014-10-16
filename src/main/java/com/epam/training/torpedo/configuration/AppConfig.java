package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.network.Client;
import com.epam.training.torpedo.network.Server;

@Configuration
public class AppConfig {

	@Bean
	Server server() {
		return new Server();
	}

	@Bean
	Client client() {
		return new Client();
	}
}
