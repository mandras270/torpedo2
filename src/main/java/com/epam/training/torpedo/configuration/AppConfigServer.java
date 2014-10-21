package com.epam.training.torpedo.configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfigServer {

	@Value("${port:1234}")
	int port;

	@Bean
	@Lazy
	ServerSocket connection() {
		try {

			ServerSocket connection = new ServerSocket(port);
			return connection;

		} catch (IOException e) {
			throw new RuntimeException("Socket creation error!", e);
		}
	}

	@Bean
	@Lazy
	Socket clientSocket() {

		try {

			Socket client = connection().accept();
			client.setTcpNoDelay(true);
			return client;

		} catch (IOException e) {
			throw new RuntimeException("Socket creation error!", e);
		}
	}

	@Bean
	@Lazy
	@Qualifier("fromClient")
	BufferedReader fromClient() {

		try {

			InputStream clientInput = clientSocket().getInputStream();
			InputStreamReader clientInputStream = new InputStreamReader(clientInput);
			BufferedReader fromClient = new BufferedReader(clientInputStream);
			return fromClient;

		} catch (IOException e) {
			throw new RuntimeException("Error in getting client's input stream!", e);
		}
	}

	@Bean
	@Lazy
	@Qualifier("toClient")
	DataOutputStream toClient() {

		try {

			OutputStream clientOutput = clientSocket().getOutputStream();
			DataOutputStream toClient = new DataOutputStream(clientOutput);
			return toClient;

		} catch (IOException e) {
			throw new RuntimeException("Error in getting client's output stream!" + e);
		}

	}
}
