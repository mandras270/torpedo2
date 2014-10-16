package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.domain.GameTable;

@Configuration
public class AppConfigGameTable {

	@Bean
	GameTable gameTable() {
		GameTable gameTable = new GameTable();
		return gameTable;
	}
}
