package com.epam.training.torpedo;

import static com.epam.training.torpedo.domain.Messages.CLIENT;
import static com.epam.training.torpedo.domain.Messages.SERVER;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.epam.training.torpedo.configuration.AppConfig;
import com.epam.training.torpedo.network.Client;
import com.epam.training.torpedo.network.Server;

public class App {

	public static void main(String[] args) {

		SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

		applicationContext.getEnvironment().getPropertySources().addFirst(ps);

		applicationContext.register(AppConfig.class);

		applicationContext.refresh();

		String gameMode = applicationContext.getBean("gameMode", String.class);

		gameMode = gameMode.toUpperCase();

		if (gameMode.equals(SERVER)) {

			Server server = applicationContext.getBean("server", Server.class);
			server.start();

		} else if (gameMode.equals(CLIENT)) {

			Client client = applicationContext.getBean("client", Client.class);

			client.start();

		}
		applicationContext.close();

	}
}
