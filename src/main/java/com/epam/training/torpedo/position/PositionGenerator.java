package com.epam.training.torpedo.position;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Position;

public class PositionGenerator implements Positionable {

	List<Position> usedPositionList;
	List<Position> resultList;

	public PositionGenerator() {
		resultList = new ArrayList<>();
		usedPositionList = new ArrayList<>();
	}

	@Override
	public List<Position> getRandomPositions(List<Position> rawPositions) {

		clearPositionList();

		shiftedPositions(rawPositions);

		return resultList;
	}

	private void clearPositionList() {
		resultList.clear();
	}

	private void shiftedPositions(List<Position> rawPositions) {

		int maxX = findMaxX(rawPositions);
		int maxY = findMaxY(rawPositions);

		List<Position> shiftedPositions = new ArrayList<>();

		do {

			int xShiftValue = getRandomShilftValue(maxX);
			int yShiftValue = getRandomShilftValue(maxY);

			shiftedPositions.clear();

			for (Position position : rawPositions) {

				Position clonedPosition = new Position(position);

				clonedPosition.shiftX(xShiftValue);
				clonedPosition.shiftY(yShiftValue);

				shiftedPositions.add(clonedPosition);
			}

		} while (!checkPositions(shiftedPositions));

		addPositionToResultList(shiftedPositions);
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
			if (usedPositionList.contains(position)) {
				return false;
			}
		}

		return true;
	}

	private void validateListIsNotEmptry( List<Position> shiftedPositions ){
		if( shiftedPositions.isEmpty() ){
			throw new IllegalArgumentException("shiftedPositions was empty!");
		}
	}
	
	private void addPositionToResultList(List<Position> positionList) {
		for (Position position : positionList) {
			resultList.add(position);
			usedPositionList.add(position);
		}
	}

	private int getRandomShilftValue(int largestCoordinate) {
		int value = (int) (Math.random() * (GameTable.NUMBER_OF_ROWS_AND_COLUMNS - 1 - largestCoordinate));
		return value;
	}

	private int findMaxX(List<Position> positionList) {
		int max = 0;

		for (Position position : positionList) {
			int x = position.getX();
			if (x > max) {
				max = x;
			}
		}

		return max;
	}

	private int findMaxY(List<Position> positionList) {
		int max = 0;

		for (Position position : positionList) {
			int y = position.getY();
			if (y > max) {
				max = y;
			}
		}

		return max;
	}
}
