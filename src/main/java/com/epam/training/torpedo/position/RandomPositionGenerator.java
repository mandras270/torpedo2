package com.epam.training.torpedo.position;

import java.util.ArrayList;
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

		resetStillAvailablePositions();

		Map<Position, Ship> shipsAndPositions = new HashMap<>();

		for (Ship ship : rawShips) {

			List<Position> rawPositions = ship.getRawPositionsList();

			List<Position> shiftedPositions = shiftedPositions(rawPositions);

			for (Position position : shiftedPositions) {
				shipsAndPositions.put(position, ship);
			}
		}

		return shipsAndPositions;
	}

	private List<Position> shiftedPositions(List<Position> rawPositions) {

		List<Position> shiftedPositions = new ArrayList<>();

		do {

			int randomPositionNumber = getRandomNumber();
			Position original = stillAvailablePositions.get(randomPositionNumber);
			int xShiftValue = original.getX();
			int yShiftValue = original.getY();

			shiftedPositions.clear();

			for (Position position : rawPositions) {

				Position clonedPosition = new Position(position);

				clonedPosition.shiftX(xShiftValue);
				clonedPosition.shiftY(yShiftValue);

				shiftedPositions.add(clonedPosition);
			}

		} while (!checkPositions(shiftedPositions));

		return removePositionsFromAvailable(shiftedPositions);
	}

	private List<Position> removePositionsFromAvailable(List<Position> positionList) {

		for (Position position : positionList) {
			stillAvailablePositions.remove(position);
		}
		return positionList;
	}

	private boolean checkPositions(List<Position> shiftedPositions) {
		validateListIsNotEmptry(shiftedPositions);
		boolean coordinatesAreNotNegative = checkCoordinatesAreNotNegative(shiftedPositions);
		boolean coordinatesAreNotInUse = checkCoordinatesAreNotInUse(shiftedPositions);
		return coordinatesAreNotInUse && coordinatesAreNotNegative;
	}

	private boolean checkCoordinatesAreNotNegative(List<Position> shiftedPositions) {
		for (Position position : shiftedPositions) {
			int x = position.getX();
			int y = position.getY();

			if (x < 0 || y < 0) {
				return false;
			}
		}
		return true;
	}

	private boolean checkCoordinatesAreNotInUse(List<Position> shiftedPositions) {

		for (Position position : shiftedPositions) {
			if (!stillAvailablePositions.contains(position)) {
				return false;
			}
		}

		return true;
	}

	private void validateListIsNotEmptry(List<Position> shiftedPositions) {
		if (shiftedPositions.isEmpty()) {
			throw new IllegalArgumentException("shiftedPositions was empty!");
		}
	}

	private int getRandomNumber() {

		int value = (int) (Math.random() * stillAvailablePositions.size());
		return value;
	}

	private void resetStillAvailablePositions() {

		stillAvailablePositions = new ArrayList<>();

		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {

				Position pos = new Position();
				pos.setX(column);
				pos.setY(row);

				stillAvailablePositions.add(pos);
			}
		}
	}
}
