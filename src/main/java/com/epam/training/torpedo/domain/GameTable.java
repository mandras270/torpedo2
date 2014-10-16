package com.epam.training.torpedo.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.torpedo.parser.ShipParser;
import com.epam.training.torpedo.position.Positionable;

public class GameTable {

	public static final int NUMBER_OF_ROWS_AND_COLUMNS = 20;

	@Autowired
	private Logger gameTableLogger;

	private Map<Position, Ship> gameTable;
	private ShipParser shipparser;
	private Positionable randomPositionGenerator;
	private int numberOfShootsFired;

	public GameTable() {
		gameTable = new HashMap<>();
		numberOfShootsFired = 0;
	}

	public void setLogger(Logger logger) {
		this.gameTableLogger = logger;
	}

	public void setShipparser(ShipParser shipparser) {
		this.shipparser = shipparser;
	}

	public void setRandomPositionGenerator(Positionable randomPositionGenerator) {
		this.randomPositionGenerator = randomPositionGenerator;
	}

	public int getNumberOfShootsFired() {
		return numberOfShootsFired;
	}

	public void addShip(Ship ship) {

		validateRandomPositionGeneratorIsNotNull();

		gameTableLogger.debug("Creating ship: " + ship);

		List<Position> parsedShipPositions = shipparser.parse(ship);

		List<Position> actualPositions = getRandomPosition(parsedShipPositions);

		System.out.println(actualPositions + " " + ship);

		addShipToTable(actualPositions, ship);
	}

	private List<Position> getRandomPosition(List<Position> original) {

		List<Position> shiftedPositions = randomPositionGenerator.getRandomPositions(original);

		return shiftedPositions;
	}

	private void validateRandomPositionGeneratorIsNotNull() {
		if (randomPositionGenerator == null) {
			throw new IllegalArgumentException("RandomPositionGenerator cannot be null");
		}
	}

	public void addAll(List<Ship> shipList) {
		for (Ship ship : shipList) {
			addShip(ship);
		}
	}

	private void addShipToTable(List<Position> shipPositions, Ship ship) {
		for (Position position : shipPositions) {
			gameTable.put(position, ship);
			gameTableLogger.debug("Ship created with: " + position);
		}
	}

	public boolean hasShipsLeft() {
		return gameTable.size() > 0;
	}

	public ShootResult shootOnPosition(Position position) {

		ShootResult result = ShootResult.NO_HIT;

		++numberOfShootsFired;

		if (gameTable.containsKey(position)) {
			Ship targetShip = gameTable.remove(position);
			result = shootOnShip(targetShip);
		}

		return result;
	}

	private ShootResult shootOnShip(Ship ship) {

		ShootResult result = ShootResult.HIT;

		ship.registerHit();

		if (!ship.isAlive()) {
			result = ShootResult.HIT_AND_SUNK;
		}

		return result;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int row = 0; row < NUMBER_OF_ROWS_AND_COLUMNS; row++) {
			for (int column = 0; column < NUMBER_OF_ROWS_AND_COLUMNS; ++column) {

				Position pos = new Position();
				pos.setX(column);
				pos.setY(row);

				if (gameTable.containsKey(pos)) {
					sb.append("O ");
				} else {
					sb.append(". ");
				}

			}
			sb.append('\n');
		}

		return sb.toString();
	}
}
