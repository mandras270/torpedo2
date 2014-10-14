package com.epam.training.torpedo.domain;

public class Ship {

	private int[][] pattern;
	private int health;

	public int[][] getPattern() {
		return pattern;
	}

	public void setPattern(int[][] pattern) {
		addPattern(pattern);
		addHealth(pattern);
	}

	private void addPattern(int[][] pattern) {
		this.pattern = pattern;
	}

	private void addHealth(int[][] pattern) {
		int health = 0;

		for (int rowIndex = 0; rowIndex < pattern.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < pattern[rowIndex].length; ++columnIndex) {
				if (pattern[rowIndex][columnIndex] == 1) {
					++health;
				}
			}
		}

		this.health = health;
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
}
