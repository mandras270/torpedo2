package com.epam.training.torpedo.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfigBasics {

	@Value("classpath:patterns.txt")
	private Resource patternData;

	@Bean
	public String patternData() {

		try (InputStream is = patternData.getInputStream()) {

			return IOUtils.toString(is);

		} catch (IOException e) {

			throw new RuntimeException("Error while reading pattern file!", e);
		}
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

		PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
		result.setFileEncoding("UTF-8");

		return result;
	}

}
