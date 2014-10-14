package com.epam.training.torpedo.domain;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.epam.training.torpedo.parser.ShipParser;

public class GameTable {

	public static final int NUMBER_OF_ROWS_AND_COLUMNS = 10;

	private Map<Position, Ship> gameTable;
	private ShipParser shipparser;

	public GameTable() {
		gameTable = new TreeMap<>();
	}

	public void setShipparser(ShipParser shipparser) {
		this.shipparser = shipparser;
	}

	public void addShip(Ship ship) {

		List<Position> shipPositions = shipparser.parse(ship);

		while()
		
		validateShipCoordinates(shipPositions);

		addShipToTable(shipPositions, ship);
	}

	public void addAll(List<Ship> shipList) {
		for (Ship ship : shipList) {
			addShip(ship);
		}
	}

	private void validateShipCoordinates(List<Position> shipPositions) {
		validateShipCoordinatesAreFree(shipPositions);
		validateShipCoordinatesAreNotOutOfTheTable(shipPositions);
	}

	private void validateShipCoordinatesAreFree(List<Position> shipPositions) {
		for (Position position : shipPositions) {
			if (gameTable.containsKey(position)) {
				throw new IllegalArgumentException("The position of this ship is already taken!");
			}
		}
	}

	private void validateCoordinateIsOnTheTable(int coordinate) {
		if (coordinate >= NUMBER_OF_ROWS_AND_COLUMNS) {
			throw new IllegalArgumentException("Coordinate is out of the table: " + coordinate);
		}
	}

	private void validateShipCoordinatesAreNotOutOfTheTable(List<Position> shipPositions) {
		for (Position position : shipPositions) {
			validateCoordinateIsOnTheTable(position.getX());
			validateCoordinateIsOnTheTable(position.getY());
		}
	}

	private void addShipToTable(List<Position> shipPositions, Ship ship) {
		for (Position position : shipPositions) {
			gameTable.put(position, ship);
		}
	}

	public boolean hasShipsLeft() {
		return gameTable.size() > 0;
	}

	public ShootResult shootOnPosition(Position position) {

		ShootResult result = ShootResult.NO_HIT;

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
		return "GameTable []";
	}
}
