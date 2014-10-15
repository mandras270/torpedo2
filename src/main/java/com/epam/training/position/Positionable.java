package com.epam.training.position;

import java.util.List;

import com.epam.training.torpedo.domain.Position;

public interface Positionable {

	public List<Position> getRandomPositions( List<Position> rawPositions );
	
}
