package com.epam.training.torpedo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.epam.training.torpedo.beanpostprocessors.CreateGameTableBeanPostProcessor;

@Configuration
public class AppConfigBasics {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

		PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
		result.setFileEncoding("UTF-8");

		return result;
	}

	@Bean
	CreateGameTableBeanPostProcessor createGameTableBeanPostProcessor() {
		return new CreateGameTableBeanPostProcessor();
	}
}
