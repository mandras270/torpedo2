package com.epam.training.torpedo.domain;

public class Position {

	private int x;
	private int y;

	public Position() {
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position otherpPosition) {
		this.x = otherpPosition.x;
		this.y = otherpPosition.y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void shiftX(int ammount) {
		this.x += ammount;
	}

	public void shiftY(int ammount) {
		this.y += ammount;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addPosition(Position otherPosition) {
		this.x += otherPosition.x;
		this.y += otherPosition.y;
	}

	public Position getTopSibling() {

		Position clonepPosition = new Position(this);

		clonepPosition.y--;

		return clonepPosition;

	}

	public Position getBottomSibling() {

		Position clonepPosition = new Position(this);

		clonepPosition.y++;

		return clonepPosition;
	}

	public Position getLeftSibling() {

		Position clonepPosition = new Position(this);

		clonepPosition.x--;

		return clonepPosition;

	}

	public Position getRightSibling() {

		Position clonepPosition = new Position(this);

		clonepPosition.x++;

		return clonepPosition;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof Position)) {
			return false;
		}

		Position other = (Position) obj;

		if (x != other.x) {
			return false;
		} else if (y != other.y) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Position [" + x + ", " + y + "]";
	}
}
