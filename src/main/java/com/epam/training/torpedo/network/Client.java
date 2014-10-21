package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.training.torpedo.domain.NetworkState;

public class Client extends Network {

	@Autowired
	@Qualifier("toServer")
	private DataOutputStream toServer;

	@Autowired
	@Qualifier("fromServer")
	private BufferedReader fromServer;

	public void setToServer(DataOutputStream toServer) {
		this.toServer = toServer;
	}

	public void setFromServer(BufferedReader fromServer) {
		this.fromServer = fromServer;
	}

	@Override
	void sendData(String data) {

		try {

			String dataWithNewLine = addMissingEndOfLineCharacter(data);
			toServer.writeBytes(dataWithNewLine);

		} catch (IOException e) {
			throw new RuntimeException("Could NOT send data to client!", e);
		}

	}

	@Override
	String readData() {

		try {

			String buffer = fromServer.readLine();
			return buffer;

		} catch (IOException e) {
			throw new RuntimeException("Could NOT read data from client!", e);
		}
	}

	private void initGameTable() {

		String welcomeMessage = readWelcomeMessage();
		int[] tableSizeParts = parseWelcomeMessage(welcomeMessage);
		int rows = tableSizeParts[0];
		int columns = tableSizeParts[1];

		gameTable.setNumberOfRows(rows);
		gameTable.setNumberOfColumn(columns);

	}

	private void initShipsOnGameTable() {
		gameTable.positionShips();
	}

	@Override
	void init() {

		initGameTable();
		initializeShooter();
		initShipsOnGameTable();
	}

	@Override
	NetworkState gameController() {

		NetworkState networkState = ShootAtEnemy();

		if (networkState == NetworkState.CONTINUE) {

			networkState = processEnemyShoot();

		}
		return networkState;
	};

	private String readWelcomeMessage() {
		String welcomeMessage = readData();
		return welcomeMessage;
	}

	private int[] parseWelcomeMessage(String welcomeMessage) {

		int[] tableSizeParts = new int[2];

		String[] parts = welcomeMessage.split(" ");

		String[] tableSize = parts[1].split(",");

		tableSizeParts[0] = Integer.valueOf(tableSize[0]);
		tableSizeParts[1] = Integer.valueOf(tableSize[1]);

		return tableSizeParts;
	}

}
