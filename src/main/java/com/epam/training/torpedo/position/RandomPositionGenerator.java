package com.epam.training.torpedo.position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.epam.training.torpedo.domain.Position;
import com.epam.training.torpedo.domain.Ship;

public class RandomPositionGenerator {

	@Value("${row:25}")
	private int numberOfRows;

	@Value("${column:25}")
	private int numberOfColumns;

	List<Position> stillAvailablePositions;

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public void setNumberOfColumn(int numberOfColumn) {
		this.numberOfColumns = numberOfColumn;
	}

	public Map<Position, Ship> getShipsAndPositions(List<Ship> rawShips) {

		Map<Position, Ship> shipsAndPositions = new HashMap<>();

		Ship ship1 = rawShips.get(0);

		shipsAndPositions.put(new Position(0, 0), ship1);
		shipsAndPositions.put(new Position(0, 1), ship1);
		shipsAndPositions.put(new Position(0, 2), ship1);
		shipsAndPositions.put(new Position(0, 3), ship1);

		ship1.setHealth(4);

		Ship ship2 = rawShips.get(1);
		shipsAndPositions.put(new Position(10, 10), ship2);
		ship2.setHealth(1);

		Ship ship3 = rawShips.get(3);
		shipsAndPositions.put(new Position(19, 19), ship3);
		ship3.setHealth(1);

		return shipsAndPositions;
	}
}
