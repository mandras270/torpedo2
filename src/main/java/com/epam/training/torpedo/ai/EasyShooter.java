package com.epam.training.torpedo.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.torpedo.domain.Position;

public class EasyShooter implements Shooter {

	@Autowired
	private Logger easyShooterLogger;

	int numberOfRows;
	int numberOfColumns;

	Position lastPosition;

	List<Position> stillAvailablePositions;
	List<Position> likelyToContainShip;

	public EasyShooter() {
		likelyToContainShip = new ArrayList<>();
		setStillAvailablePositions();
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

		return bestPosition;
	}

	@Override
	public void registerLastShootHit() {

		addBottomSibling();
		addLeftSibling();
		addRightSibling();
		addTopSibling();

	}

	private void addLeftSibling() {

		Position leftSibling = lastPosition.getLeftSibling();

		if (positionIsValid(lastPosition)) {
			likelyToContainShip.add(leftSibling);
		}

	}

	private void addRightSibling() {
		Position rightSibling = lastPosition.getRightSibling();

		if (positionIsValid(rightSibling)) {
			likelyToContainShip.add(rightSibling);
		}
	}

	private void addTopSibling() {
		Position topSibling = lastPosition.getTopSibling();

		if (positionIsValid(topSibling)) {
			likelyToContainShip.add(topSibling);
		}
	}

	private void addBottomSibling() {

		Position bottomSibling = lastPosition.getBottomSibling();

		if (positionIsValid(bottomSibling)) {
			likelyToContainShip.add(bottomSibling);
		}

	}

	@Override
	public void registerLastShootMissed() {
		easyShooterLogger.debug("Shoot missed: " + lastPosition);
	}

	@Override
	public void registerLastShootSunk() {
		easyShooterLogger.debug("Enemy ship has sunk: " + lastPosition);
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

	private void setStillAvailablePositions() {
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
