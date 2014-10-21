package com.epam.training.torpedo.network;

import static com.epam.training.torpedo.domain.Messages.FIRE;
import static com.epam.training.torpedo.domain.Messages.HIT;
import static com.epam.training.torpedo.domain.Messages.MISSED;
import static com.epam.training.torpedo.domain.Messages.SUNK;
import static com.epam.training.torpedo.domain.Messages.WON;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.ai.Shooter;
import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Loggable;
import com.epam.training.torpedo.domain.Messages;
import com.epam.training.torpedo.domain.NetworkState;
import com.epam.training.torpedo.domain.Position;
import com.epam.training.torpedo.domain.ShootAction;

public abstract class Network implements Loggable {

	Logger logger;

	@Autowired
	Shooter shooter;

	@Autowired
	GameTable gameTable;

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setShooter(EasyShooter shooter) {
		this.shooter = shooter;
	}

	public void setGameTable(GameTable gameTable) {
		this.gameTable = gameTable;
	}

	void initializeShooter() {

		int numberOfColums = gameTable.getNumberOfColumn();

		int numberOfRows = gameTable.getNumberOfRows();

		shooter.setNumberOfColumns(numberOfColums);
		shooter.setNumberOfRows(numberOfRows);

		shooter.init();

	}

	ShootAction parseEnemyShoot(String data) {

		ShootAction result = null;

		String[] dataParts = data.split(" ");
		String command = dataParts[0];

		if (command.equals(Messages.FIRE)) {

			result = registerShootOnTable(dataParts[1]);

		} else {

			result = ShootAction.ERROR;
			logger.debug("Unknow message received! " + data);

		}

		return result;
	}

	NetworkState getNetworkStateBasedOnResponsData(ShootAction responseData) {

		NetworkState state = NetworkState.CONTINUE;

		if (responseData == ShootAction.ERROR) {

			state = NetworkState.FINISHED;

		} else if (responseData == ShootAction.WON) {

			state = NetworkState.FINISHED;
		}

		return state;
	}

	ShootAction registerShootOnTable(String rawCordinates) {

		String[] part = rawCordinates.split(",");

		int x = Integer.valueOf(part[0]);
		int y = Integer.valueOf(part[1]);

		Position position = new Position(x, y);

		ShootAction shootResult = gameTable.shootOnPosition(position);

		return shootResult;
	}

	String getFirePosition() {
		Position markedPosition = shooter.shoot();

		String fireMessage = FIRE + " " + markedPosition.getX() + "," + markedPosition.getY();

		return fireMessage;
	}

	NetworkState parseResponseOnShoot(String response) {

		NetworkState result = null;

		String[] parts = response.split(" ");
		String command = parts[0];

		if (command.equals(HIT)) {

			shooter.registerLastShootHit();
			result = NetworkState.CONTINUE;

		} else if (command.equals(SUNK)) {

			shooter.registerLastShootSunk();
			result = NetworkState.CONTINUE;

		} else if (command.equals(MISSED)) {

			shooter.registerLastShootMissed();
			result = NetworkState.CONTINUE;

		} else if (command.equals(WON)) {

			announceWon();
			result = NetworkState.FINISHED;

		} else {

			sayError(response);
			result = NetworkState.FINISHED;

		}

		return result;
	}

	String addMissingEndOfLineCharacter(String data) {

		String dataWithNewLine = data;
		String newLine = System.getProperty("line.separator");

		if (!dataWithNewLine.contains(newLine)) {
			dataWithNewLine += newLine;
		}

		return dataWithNewLine;
	}

	NetworkState ShootAtEnemy() {

		String firePosition = getFirePosition();
		sendData(firePosition);

		String clientResponse = readData();
		NetworkState parsedClientResponse = parseResponseOnShoot(clientResponse);

		return parsedClientResponse;
	}

	NetworkState processEnemyShoot() {

		String dataFromClient = readData();

		ShootAction shootResult = parseEnemyShoot(dataFromClient);

		String responseData = shootResult.toString();

		sendData(responseData);

		NetworkState networkStateBasedOnResponsData = getNetworkStateBasedOnResponsData(shootResult);

		return networkStateBasedOnResponsData;
	}

	void sayError(String errorMessage) {
		logger.debug("Unexpected message: " + errorMessage);
	}

	void announceWon() {
		logger.debug("Congratulations, you won! It took " + gameTable.getNumberOfShootsFired()
				+ " shoots");
	}

	public void start() {

		init();

		NetworkState networkState = NetworkState.CONTINUE;

		logger.debug('\n' + gameTable.toString());

		while (networkState == NetworkState.CONTINUE) {

			networkState = gameController();
		}

	}

	abstract void init();

	abstract NetworkState gameController();

	abstract void sendData(String data);

	abstract String readData();

}
