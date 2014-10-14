package com.epam.training.torpedo.ai;

import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Position;

public class EasyShooter implements Shooter {

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
