package com.epam.training.torpedo.ai;

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
		return null;
	}
}
