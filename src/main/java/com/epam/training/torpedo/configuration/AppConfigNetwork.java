package com.epam.training.torpedo.configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigNetwork {

	private static final int PORT = 1234;

	@Bean
	ServerSocket connection() {
		try {

			ServerSocket connection = new ServerSocket(PORT);
			return connection;

		} catch (IOException e) {
			throw new RuntimeException("Socket creation error!", e);
		}
	}

	@Bean
	Socket client() {

		try {

			Socket client = connection().accept();
			return client;

		} catch (IOException e) {
			throw new RuntimeException("Socket creation error!", e);
		}
	}

	
}
