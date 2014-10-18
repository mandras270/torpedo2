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
import com.epam.training.torpedo.domain.NetworkState;
import com.epam.training.torpedo.domain.Position;
import com.epam.training.torpedo.domain.ResponseData;

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

	private String addMissingEndOfLineCharacter(String data) {

		String dataWithNewLine = data;
		String newLine = System.getProperty("line.separator");

		if (!dataWithNewLine.contains(newLine)) {
			dataWithNewLine += newLine;
		}

		return dataWithNewLine;
	}

	private void sendDataToClient(String data) {

		try {

			String dataWithNewLine = addMissingEndOfLineCharacter(data);
			toClient.writeBytes(dataWithNewLine);

		} catch (IOException e) {
			throw new RuntimeException("Could NOT send data to client!", e);
		}

	}

	private String readDataFromClient() {

		try {

			String buffer = fromClient.readLine();
			return buffer;

		} catch (IOException e) {
			throw new RuntimeException("Could not read data from client!", e);
		}
	}

	public void start() {

		NetworkState networkState = NetworkState.CONTINUE;

		while (networkState == NetworkState.CONTINUE) {

			networkState = gameController();
		}

	}

	private NetworkState gameController() {

		NetworkState networkState = processEnemyShoot();

		if (networkState == NetworkState.CONTINUE) {

			networkState = ShootAtEnemy();

		}

		return networkState;

	}

	private NetworkState processEnemyShoot() {

		String dataFromClient = readDataFromClient();

		ResponseData shootResult = parseClientData(dataFromClient);

		String responseData = shootResult.toString();

		sendDataToClient(responseData);

		NetworkState networkStateBasedOnResponsData = getNetworkStateBasedOnResponsData(shootResult);

		return networkStateBasedOnResponsData;
	}

	private ResponseData parseClientData(String dataFromClient) {

		String[] dataParts = dataFromClient.split(" ");
		ResponseData result = null;

		switch (dataParts[0]) {
		case "FIRE":
			result = registerShootOnTable(dataParts[1]);
			break;
		default:
			result = ResponseData.ERROR;
			serverLogger.debug("Unknow message recived! " + dataFromClient);
			break;
		}

		return result;
	}

	private NetworkState getNetworkStateBasedOnResponsData(ResponseData responseData) {

		NetworkState state = NetworkState.CONTINUE;

		if (responseData == ResponseData.ERROR) {

			state = NetworkState.FINISHED;

		} else if (responseData == ResponseData.WON) {

			state = NetworkState.FINISHED;
		}

		return state;
	}

	private ResponseData registerShootOnTable(String rawCordinates) {

		String[] part = rawCordinates.split(",");

		int x = Integer.valueOf(part[0]);
		int y = Integer.valueOf(part[1]);

		Position position = new Position(x, y);

		ResponseData shootResult = gameTable.shootOnPosition(position);

		return shootResult;
	}

	private NetworkState ShootAtEnemy() {

		String firePosition = getFirePosition();
		sendDataToClient(firePosition);

		String clientResponse = readDataFromClient();
		NetworkState parsedClientResponse = parseClientResponseOnShoot(clientResponse);

		return parsedClientResponse;
	}

	private String getFirePosition() {
		Position markedPosition = shooter.shoot();

		String fireMessage = "FIRE " + markedPosition.getX() + ", " + markedPosition.getY();

		return fireMessage;
	}

	private NetworkState parseClientResponseOnShoot(String shoot) {

		String[] parts = shoot.split(" ");
		NetworkState result = null;

		switch (parts[0]) {
		case "HIT":
			// register HIT
			break;
		case "SUNK":
			// register SUNK
			break;
		case "MISSED":
			// register miss
			break;
		case "WON":
			// saywon
			break;
		case "ERROR":
			// register error
			break;
		default:
			// unknow
			// register quir!!
		}

		return result;
	}
}