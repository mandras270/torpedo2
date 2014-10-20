package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.training.torpedo.parser.RawPositionParser;

@Configuration
public class AppConfigRawPositionParser {

	@Bean
	RawPositionParser rawPositionParser() {
		return new RawPositionParser();
	}

}
