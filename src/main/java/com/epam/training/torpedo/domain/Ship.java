package com.epam.training.torpedo.domain;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	private int health;

	private List<Position> rawPositionsList;

	public Ship() {
		super();
		rawPositionsList = new ArrayList<>();
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public List<Position> getRawPositionsList() {
		return rawPositionsList;
	}

	public void addRawPositions(List<Position> rawPositionsList) {
		this.rawPositionsList = rawPositionsList;
	}

	public boolean isAlive() {
		return health > 0;
	}

	public void registerHit() {
		validateShipIsStillAlive();
		decreaseHealth();
	}

	private void validateShipIsStillAlive() {
		if (health == 0) {
			throw new IllegalArgumentException("This ship is already down.");
		}
	}

	private void decreaseHealth() {
		health--;
	}

	@Override
	public String toString() {
		String result = "Ship - " + health;
		return result;
	}
}
