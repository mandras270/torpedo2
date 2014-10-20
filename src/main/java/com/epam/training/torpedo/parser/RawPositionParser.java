package com.epam.training.torpedo.parser;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.torpedo.domain.Position;

public class RawPositionParser {

	public List<Position> parse(Integer[][] shipPattern) {

		List<Position> result = doParse(shipPattern);

		return result;
	}

	private List<Position> doParse(Integer[][] shipPattern) {

		int yPadding = getXPadding(shipPattern);
		int xPadding = getYPadding(shipPattern);

		List<Position> rawList = getRawPositionList(shipPattern);

		for (Position position : rawList) {
			position.shiftX(-xPadding);
			position.shiftY(-yPadding);
		}

		return rawList;
	}

	private List<Position> getRawPositionList(Integer[][] shipPattern) {

		List<Position> result = new ArrayList<>();

		for (int rowIndex = 0; rowIndex < shipPattern.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < shipPattern[rowIndex].length; ++columnIndex) {

				if (shipPattern[columnIndex][rowIndex] == 1) {
					Position rawPosition = getNewPositionWithCoordinates(rowIndex, columnIndex);
					result.add(rawPosition);
				}
			}
		}
		return result;
	}

	private Position getNewPositionWithCoordinates(int x, int y) {
		Position result = new Position();
		result.setX(x);
		result.setY(y);
		return result;
	}

	private int getXPadding(Integer[][] shipPattern) {

		for (int rowIndex = 0; rowIndex < shipPattern.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < shipPattern[rowIndex].length; ++columnIndex) {
				if (shipPattern[columnIndex][rowIndex] == 1) {
					return rowIndex;
				}
			}
		}
		throw new IllegalArgumentException("could not found x padding");
	}

	private int getYPadding(Integer[][] shipPattern) {

		for (int rowIndex = 0; rowIndex < shipPattern.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < shipPattern[rowIndex].length; ++columnIndex) {
				if (shipPattern[rowIndex][columnIndex] == 1) {
					return rowIndex;
				}
			}
		}
		throw new IllegalArgumentException("could not found y padding");
	}

}
