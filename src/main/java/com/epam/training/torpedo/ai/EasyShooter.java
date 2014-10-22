package com.epam.training.torpedo.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.epam.training.torpedo.domain.Loggable;
import com.epam.training.torpedo.domain.Position;

public class EasyShooter implements Shooter, Loggable {

	private Logger logger;

	int numberOfRows;
	int numberOfColumns;

	Position lastPosition;

	List<Position> stillAvailablePositions;
	List<Position> likelyToContainShip;

	public EasyShooter() {
		likelyToContainShip = new ArrayList<>();
	}

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	@Override
	public void setNumberOfColumns(int numberOfColums) {
		this.numberOfColumns = numberOfColums;
	}

	@Override
	public Position shoot() {

		Position bestPosition = null;

		if (!likelyToContainShip.isEmpty()) {

			bestPosition = likelyToContainShip.remove(0);

		} else if (!stillAvailablePositions.isEmpty()) {

			bestPosition = stillAvailablePositions.remove(0);

		} else {
			throw new IllegalArgumentException("Out of positions to shoot on!");
		}

		lastPosition = bestPosition;

		return bestPosition;
	}

	@Override
	public void registerLastShootHit() {

		addLeftSibling();
		addRightSibling();

	}

	private void addLeftSibling() {

		Position leftSibling = lastPosition.getLeftSibling();

		if (positionIsValid(leftSibling) && stillAvailablePositions.contains(leftSibling)) {
			stillAvailablePositions.remove(leftSibling);
			likelyToContainShip.add(leftSibling);
		}

	}

	private void addRightSibling() {
		Position rightSibling = lastPosition.getRightSibling();

		if (positionIsValid(rightSibling) && stillAvailablePositions.contains(rightSibling)) {
			stillAvailablePositions.remove(rightSibling);
			likelyToContainShip.add(rightSibling);
		}
	}

	@Override
	public void registerLastShootMissed() {
	}

	@Override
	public void registerLastShootSunk() {
	}

	private boolean positionIsValid(Position position) {

		boolean result = true;
		int x = position.getX();
		int y = position.getY();

		if (x < 0 || x >= numberOfColumns) {
			result = false;
		}

		if (y < 0 || y >= numberOfRows) {
			result = false;
		}

		return result;
	}

	@Override
	public void init() {

		List<Position> stillAvailablePositions = new ArrayList<>();

		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; ++column) {

				Position pos = new Position();
				pos.setX(column);
				pos.setY(row);

				stillAvailablePositions.add(pos);
			}
		}
		Collections.shuffle(stillAvailablePositions);
		this.stillAvailablePositions = stillAvailablePositions;
	}
}
