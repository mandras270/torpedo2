package com.epam.training.torpedo;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.epam.training.torpedo.configuration.AppConfig;
import com.epam.training.torpedo.network.Server;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

		applicationContext.getEnvironment().getPropertySources().addFirst(ps);

		applicationContext.register(AppConfig.class);

		applicationContext.refresh();

		Server server = applicationContext.getBean("server", Server.class);

		server.start();

		applicationContext.close();

	}
}
