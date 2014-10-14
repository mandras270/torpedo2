package com.epam.training.torpedo;

import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.domain.GameController;
import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.parser.SimpleShipParser;

public class App {
	public static void main(String[] args) {

		GameController gameController = new GameController();
		EasyShooter easyShooter = new EasyShooter();

		gameController.setShooter(easyShooter);
		
		GameTable gameTable = new GameTable();
		SimpleShipParser shipparser = new SimpleShipParser();
		gameTable.setShipparser(shipparser);
		
		gameController.setGameTable(gameTable);
		
		gameController.keepShootingTillAllShipsGoDown();

	}
}
