package com.epam.training.torpedo.configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigClient {

	@Value("${port:1234}")
	int port;

	@Value("${server:127.0.0.1}")
	String serverIp;

	@Bean
	Socket server() {
		try {

			Socket server = new Socket(serverIp, port);
			return server;

		} catch (IOException e) {
			throw new IllegalArgumentException("Could NOT connect to server", e);
		}
	}

	@Bean
	DataOutputStream ToServer() {

		try {

			DataOutputStream toServer = new DataOutputStream(server()
					.getOutputStream());
			return toServer;

		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Could NOT get server output stream", e);
		}
	}

	@Bean
	InputStreamReader InputStreamReader(InputStream in) {
		return new InputStreamReader(in);
	}

	@Bean
	BufferedReader FromServer() {

		try {
			BufferedReader FromServer = new BufferedReader(
					InputStreamReader(server().getInputStream()));
			return FromServer;
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Could NOT get server input stream!", e);
		}
	}
}
