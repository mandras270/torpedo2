package com.epam.training.torpedo.parser;

import java.util.List;

import com.epam.training.torpedo.domain.Position;
import com.epam.training.torpedo.domain.Ship;

public interface ShipParser {

	public List<Position> parse( Ship ship);
	
}
