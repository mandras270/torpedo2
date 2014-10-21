package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.training.torpedo.domain.NetworkState;
import com.epam.training.torpedo.domain.ShootAction;

public class Server extends Network {

	@Autowired
	@Qualifier("fromClient")
	private BufferedReader fromClient;

	@Autowired
	@Qualifier("toClient")
	private DataOutputStream toClient;

	public void setFromClient(BufferedReader fromClient) {
		this.fromClient = fromClient;
	}

	public void setToClient(DataOutputStream toClient) {
		this.toClient = toClient;
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
			throw new RuntimeException("Could NOT read data from client!", e);
		}
	}

	@Override
	void init() {

		initializeShooter();
		sendWelcomeMessage();

	}

	private void sendWelcomeMessage() {

		String welcomeMessageWithoutEndOfLine = "WELCOME " + gameTable.getNumberOfRows() + ","
				+ gameTable.getNumberOfColumn();

		String welcomeMessage = addMissingEndOfLineCharacter(welcomeMessageWithoutEndOfLine);

		sendDataToClient(welcomeMessage);

	}

	@Override
	NetworkState gameController() {

		NetworkState networkState = processEnemyShoot();

		if (networkState == NetworkState.CONTINUE) {

			networkState = ShootAtEnemy();

		}

		return networkState;

	}

	private NetworkState processEnemyShoot() {

		String dataFromClient = readDataFromClient();

		ShootAction shootResult = parseEnemyShoot(dataFromClient);

		String responseData = shootResult.toString();

		sendDataToClient(responseData);

		NetworkState networkStateBasedOnResponsData = getNetworkStateBasedOnResponsData(shootResult);

		return networkStateBasedOnResponsData;
	}

	private NetworkState ShootAtEnemy() {

		String firePosition = getFirePosition();
		sendDataToClient(firePosition);

		String clientResponse = readDataFromClient();
		NetworkState parsedClientResponse = parseResponseOnShoot(clientResponse);

		return parsedClientResponse;
	}

}