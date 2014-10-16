package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

	private BufferedReader fromClient;
	private DataOutputStream toClient;

	private Shooter shooter;
	private GameTable gameTable;

	public void setLOGGER(Logger lOGGER) {
		serverLogger = lOGGER;
	}

	public void setConnection(ServerSocket connection) {
		addConnection(connection);
		addClient();
		addClientInputStream();
		addClientOutputStream();
		serverLogger.debug("Client (" + client.getInetAddress().getHostAddress() + ") connected.");
	}

	public void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	public void setGameTable(GameTable gameTable) {
		this.gameTable = gameTable;
	}

	/* This setter is needed for unit tests */
	public void setFromClient(BufferedReader fromClient) {
		this.fromClient = fromClient;
	}

	/* This setter is needed for unit tests */
	public void setToClient(DataOutputStream toClient) {
		this.toClient = toClient;
	}

	private void addConnection(ServerSocket connection) {
		this.connection = connection;
	}

	private void addClient() {

		try {

			client = connection.accept();

		} catch (IOException e) {
			throw new RuntimeException("Could not create the socket");
		}
	}

	private void addClientInputStream() {

		InputStream clientInput;
		try {

			clientInput = client.getInputStream();
			InputStreamReader clientInputStream = new InputStreamReader(clientInput);
			fromClient = new BufferedReader(clientInputStream);

		} catch (IOException e) {
			throw new RuntimeException("Could not get client's InputStream." + e);
		}
	}

	private void addClientOutputStream() {

		OutputStream clientOutput;
		try {

			clientOutput = client.getOutputStream();
			toClient = new DataOutputStream(clientOutput);

		} catch (IOException e) {
			throw new RuntimeException("Could not get client's OutputStream." + e);
		}
	}

	private String readDataFromClient() {

		String buffer;
		try {

			buffer = fromClient.readLine();

		} catch (IOException e) {
			throw new RuntimeException("Could not read data from client.", e);
		}

		return buffer;
	}

	private void sendDataToClient(String data) {

		try {

			toClient.writeBytes(data + "\n");

		} catch (IOException e) {
			throw new RuntimeException("Could not send data to client");
		}

	}

	public void start(){}
	
	public void close() {

		try {
			client.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not close client socket", e);
		}

		try {
			connection.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not close server socket", e);
		}

	}

}
