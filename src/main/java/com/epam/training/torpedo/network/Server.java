package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.torpedo.ai.Shooter;
import com.epam.training.torpedo.domain.GameTable;

public class Server {

	@Autowired
	private Logger serverLogger;

	@Autowired
	private ServerSocket connection;

	@Autowired
	private Socket client;

	@Autowired
	private BufferedReader fromClient;

	@Autowired
	private DataOutputStream toClient;

	@Autowired
	private Shooter shooter;

	@Autowired
	private GameTable gameTable;

	public void setServerLogger(Logger serverLogger) {
		this.serverLogger = serverLogger;
	}

	public void setConnection(ServerSocket connection) {
		this.connection = connection;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public void setFromClient(BufferedReader fromClient) {
		this.fromClient = fromClient;
	}

	public void setToClient(DataOutputStream toClient) {
		this.toClient = toClient;
	}

	public void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	public void setGameTable(GameTable gameTable) {
		this.gameTable = gameTable;
	}

	private String readDataFromClient() {

		String buffer;
		try {

			buffer = fromClient.readLine();

		} catch (IOException e) {
			throw new RuntimeException("Could not read data from client!", e);
		}

		return buffer;
	}

	private void sendDataToClient(String data) {

		try {

			toClient.writeBytes(data + "\n");

		} catch (IOException e) {
			throw new RuntimeException("Could NOT send data to client!", e);
		}

	}

	public void start() {
		serverLogger.debug("Client (" + client.getInetAddress().getHostAddress() + ") connected.");
	}

}
