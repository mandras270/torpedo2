package com.epam.training.torpedo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.epam.training.torpedo.ai.RandomPositionGenerator;

public class GameTable {

	@Value("${row:25}")
	private int numberOfRows;

	@Value("${column:25}")
	private int numberOfColumn;

	@Autowired
	private Logger gameTableLogger;

	@Autowired
	private RandomPositionGenerator positionGenerator;

	@Resource(name = "rawShips")
	private List<Ship> rawShips;

	private Map<Position, Ship> gameTable;

	private List<Position> sunkShips;

	private int numberOfShootsFired;

	public GameTable() {
		sunkShips = new ArrayList<>();
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

	public void positionShips() {
		gameTable = positionGenerator.getShipsAndPositions(rawShips);
	}

	public boolean hasShipsLeft() {
		return gameTable.size() > 0;
	}

	public ResponseData shootOnPosition(Position position) {

		ResponseData result = ResponseData.MISSED;

		++numberOfShootsFired;

		if (gameTable.containsKey(position)) {

			Ship targetShip = gameTable.remove(position);

			sunkShips.add(position);

			result = shootOnShip(targetShip);

		}

		if (!hasShipsLeft()) {
			result = ResponseData.WON;
		}

		return result;
	}

	private ResponseData shootOnShip(Ship ship) {

		ResponseData result = ResponseData.HIT;

		ship.registerHit();

		if (!ship.isAlive()) {
			result = ResponseData.SUNK;
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
				} else if (sunkShips.contains(pos)) {
					sb.append("X ");
				} else {
					sb.append(". ");
				}

			}
			sb.append('\n');
		}

		return sb.toString();
	}
}
