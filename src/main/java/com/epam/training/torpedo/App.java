package com.epam.training.torpedo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.training.filereader.ShipFileReader;
import com.epam.training.position.PositionGenerator;
import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.domain.GameController;
import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.domain.Ship;
import com.epam.training.torpedo.parser.SimpleShipParser;

public class App {
	public static void main(String[] args) throws FileNotFoundException {

		GameController gameController = new GameController();
		EasyShooter easyShooter = new EasyShooter();

		gameController.setShooter(easyShooter);

		Logger gameTableLogger = LoggerFactory.getLogger(GameTable.class);
		GameTable.setLOGGER(gameTableLogger);
		GameTable gameTable = new GameTable();

		SimpleShipParser shipparser = new SimpleShipParser();
		PositionGenerator positionGenerator = new PositionGenerator();

		ShipFileReader reader = new ShipFileReader();

		reader.setReader(new BufferedReader(new FileReader("patterns.txt")));

		List<Ship> shipList = reader.readShipsFromFile();

		gameTable.setShipparser(shipparser);
		gameTable.setRandomPositionGenerator(positionGenerator);
		gameTable.addAll(shipList);

		gameController.setGameTable(gameTable);
		
		System.out.println( gameTable );

		gameController.keepShootingTillAllShipsGoDown();

	}
}
