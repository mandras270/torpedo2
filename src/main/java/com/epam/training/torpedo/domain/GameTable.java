package com.epam.training.torpedo.domain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class GameTable {

	@Value("${row:25}")
	private int numberOfRows;

	@Value("${column:25}")
	private int numberOfColumn;

	@Autowired
	private Logger gameTableLogger;

	private Map<Position, Ship> gameTable;
	private int numberOfShootsFired;

	public GameTable() {
		gameTable = new HashMap<>();
		numberOfShootsFired = 0;
	}

	public void setLogger(Logger logger) {
		this.gameTableLogger = logger;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumn() {
		return numberOfColumn;
	}

	public int getNumberOfShootsFired() {
		return numberOfShootsFired;
	}

	public void addShip(Ship ship, Position position) {
		gameTable.put(position, ship);
	}

	public void addAll(Map<Position, Ship> shipMap) {
		gameTable.putAll(shipMap);
	}

	public boolean hasShipsLeft() {
		return gameTable.size() > 0;
	}

	public ShootResult shootOnPosition(Position position) {

		ShootResult result = ShootResult.MISSED;

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
			result = ShootResult.SUNK;
		}

		return result;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumn; ++column) {

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
