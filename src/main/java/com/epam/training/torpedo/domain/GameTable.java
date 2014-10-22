package com.epam.training.torpedo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.epam.training.torpedo.position.RandomPositionGenerator;

public class GameTable implements Loggable {

	private Logger logger;

	@Value("${row:25}")
	private int numberOfRows;

	@Value("${column:25}")
	private int numberOfColumn;

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

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
		positionGenerator.setNumberOfRows(numberOfRows);
	}

	public void setNumberOfColumn(int numberOfColumn) {
		this.numberOfColumn = numberOfColumn;
		positionGenerator.setNumberOfColumn(numberOfColumn);
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

	public ShootAction shootOnPosition(Position position) {

		ShootAction result = ShootAction.MISSED;

		++numberOfShootsFired;

		if (gameTable.containsKey(position)) {

			Ship targetShip = gameTable.remove(position);

			sunkShips.add(position);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			logger.debug('\n' + this.toString());

			result = shootOnShip(targetShip);

		}

		if (!hasShipsLeft()) {
			result = ShootAction.WON;
		}

		return result;
	}

	private ShootAction shootOnShip(Ship ship) {

		ShootAction result = ShootAction.HIT;

		ship.registerHit();

		if (!ship.isAlive()) {
			result = ShootAction.SUNK;
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
