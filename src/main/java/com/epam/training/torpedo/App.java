package com.epam.training.torpedo;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.training.torpedo.network.Server;

public class App {
	public static void main(String[] args) throws FileNotFoundException {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				"com.epam.training.torpedo.configuration");

		Server server = applicationContext.getBean("server", Server.class);

		server.start();

		applicationContext.close();

	}
}
