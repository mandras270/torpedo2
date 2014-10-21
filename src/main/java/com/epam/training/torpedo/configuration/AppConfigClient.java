package com.epam.training.torpedo.configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfigClient {

	@Value("${port:1234}")
	int port;

	@Value("${server:127.0.0.1}")
	String serverIp;

	@Bean
	@Lazy
	Socket serverConnection() {
		try {

			Socket server = new Socket(serverIp, port);
			server.setTcpNoDelay(true);
			return server;

		} catch (IOException e) {
			throw new IllegalArgumentException("Could NOT connect to server", e);
		}
	}

	@Bean
	@Lazy
	@Qualifier("toServer")
	DataOutputStream toServer() {

		try {

			DataOutputStream toServer = new DataOutputStream(serverConnection().getOutputStream());
			return toServer;

		} catch (IOException e) {
			throw new IllegalArgumentException("Could NOT get server output stream", e);
		}
	}

	InputStreamReader inputStreamReader(InputStream in) {
		return new InputStreamReader(in);
	}

	@Bean
	@Lazy
	@Qualifier("fromServer")
	BufferedReader fromServer() {

		try {
			BufferedReader FromServer = new BufferedReader(inputStreamReader(serverConnection()
					.getInputStream()));
			return FromServer;
		} catch (IOException e) {
			throw new IllegalArgumentException("Could NOT get server input stream!", e);
		}
	}
}
