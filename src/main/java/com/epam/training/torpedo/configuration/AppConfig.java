package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import com.epam.training.torpedo.network.Client;
import com.epam.training.torpedo.network.Server;

@Configuration
@Import({ AppConfigBasics.class, AppConfigGameTable.class,
		AppConfigLogger.class, AppConfigServer.class, AppConfigShooter.class,
		AppConfigShip.class, AppConfigRawPositionParser.class,
		AppConfigRandomPositionGenerator.class, AppConfigClient.class })
public class AppConfig {

	@Bean
	@Lazy
	Server server() {
		return new Server();
	}

	@Bean
	@Lazy
	Client client() {
		return new Client();
	}
}
