package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.epam.training.torpedo.network.Client;
import com.epam.training.torpedo.network.Server;

@Configuration
@Import({ AppConfigBasics.class, AppConfigGameTable.class, AppConfigLogger.class,
		AppConfigNetwork.class, AppConfigShooter.class, AppConfigShip.class,
		AppConfigRawPositionParser.class, AppConfigRandomPositionGenerator.class })
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
