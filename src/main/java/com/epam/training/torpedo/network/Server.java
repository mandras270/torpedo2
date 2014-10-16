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
import com.epam.training.torpedo.domain.ShootResult;

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

			String dataWithNewLine = addNewLine(data);
			toClient.writeBytes(dataWithNewLine);

		} catch (IOException e) {
			throw new RuntimeException("Could NOT send data to client!", e);
		}

	}

	private String addNewLine(String data) {

		String dataWithNewLine = data;
		String newLine = System.getProperty("line.separator");

		if (!dataWithNewLine.contains(newLine)) {
			dataWithNewLine += newLine;
		}

		return dataWithNewLine;
	}

	private ShootResult parseClientData(String dataFromClient) {
		return ShootResult.MISSED;
	}

	public void start() {

		String welcome = gameTable.toString();
		sendDataToClient(welcome);

		while (gameTable.hasShipsLeft()) {

			String dataFromClient = readDataFromClient();

			ShootResult result = parseClientData(dataFromClient);

			sendDataToClient(result.toString());

		}

	}

}
