package com.epam.training.torpedo.ai;

import com.epam.training.torpedo.domain.Position;

public interface Shooter {

	public void setNumberOfRows(int numberOfRows);

	public void setNumberOfColumns(int numberOfColums);

	public Position shoot();

	public void registerLastShootHit();

	public void registerLastShootMissed();

	public void registerLastShootSunk();

	public void init();
}
