package com.epam.training.torpedo.position;

import java.util.List;

import com.epam.training.torpedo.domain.Position;

public interface Positionable {

	public List<Position> getRandomPositions( List<Position> rawPositions );
	
}
