package com.epam.training.torpedo.ai;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Position;

public class EasyShooter implements Shooter {

	@Autowired
	private Logger easyShooterLogger;

	int numberOfRows;

	int numberOfColumns;

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	@Override
	public Position shoot() {
		int x = getRandomCoordinate();
		int y = getRandomCoordinate();
		return getNewPositionWithCoordinates(x, y);
	}

	private int getRandomCoordinate() {
		int result = (int) (Math.random() * GameTable.NUMBER_OF_ROWS_AND_COLUMNS);
		return result;
	}

	private Position getNewPositionWithCoordinates(int x, int y) {
		Position result = new Position();
		result.setX(x);
		result.setY(y);
		return result;
	}

}
