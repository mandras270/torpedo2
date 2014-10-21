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

	private void sendDataToServer(String data) {

		try {

			String dataWithNewLine = addMissingEndOfLineCharacter(data);
			toServer.writeBytes(dataWithNewLine);

		} catch (IOException e) {
			throw new RuntimeException("Could NOT send data to client!", e);
		}

	}

	private String readDataFromServer() {

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
		// TODO Auto-generated method stub
		return null;
	};

	private String readWelcomeMessage() {
		String welcomeMessage = readDataFromServer();
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

	private NetworkState ShootAtEnemy() {

		String firePosition = getFirePosition();
		sendDataToServer(firePosition);

		String clientResponse = readDataFromServer();
		NetworkState parsedClientResponse = parseResponseOnShoot(clientResponse);

		return parsedClientResponse;
	}
}
