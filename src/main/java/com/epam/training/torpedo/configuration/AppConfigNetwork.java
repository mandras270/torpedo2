package com.epam.training.torpedo.configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

	@Bean
	BufferedReader fromClient() {

		try {

			InputStream clientInput = client().getInputStream();
			InputStreamReader clientInputStream = new InputStreamReader(clientInput);
			BufferedReader fromClient = new BufferedReader(clientInputStream);
			return fromClient;

		} catch (IOException e) {
			throw new RuntimeException("Error in getting client's input stream!", e);
		}
	}

	@Bean
	DataOutputStream toClient() {

		try {

			OutputStream clientOutput = client().getOutputStream();
			DataOutputStream toClient = new DataOutputStream(clientOutput);
			return toClient;

		} catch (IOException e) {
			throw new RuntimeException("Error in getting client's output stream!" + e);
		}

	}
}
