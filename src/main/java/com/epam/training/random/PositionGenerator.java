package com.epam.training.random;

import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Position;

public class PositionGenerator implements RandomPositionable {

	@Override
	public Position getRandomPosition() {

		int x = (int) (Math.random() * GameTable.NUMBER_OF_ROWS_AND_COLUMNS);
		int y = (int) (Math.random() * GameTable.NUMBER_OF_ROWS_AND_COLUMNS);

		Position position = new Position();
		position.setX(x);
		position.setY(y);
		return position;
	}

}
