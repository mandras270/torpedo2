package com.epam.training.torpedo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.training.torpedo.domain.GameTable;
import com.epam.training.torpedo.network.Server;

public class App {
	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			
			Logger serverLogger = LoggerFactory.getLogger(Server.class);
			ServerSocket connection = new ServerSocket(1234);
			Server server = new Server();
			server.setLOGGER(serverLogger);
			server.setConnection(connection);
			
			Logger gameTable = LoggerFactory.getLogger(GameTable.class);
			GameTable game = new GameTable();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

//		GameController gameController = new GameController();
//		EasyShooter easyShooter = new EasyShooter();
//
//		gameController.setShooter(easyShooter);
//
//		Logger gameTableLogger = LoggerFactory.getLogger(GameTable.class);
//		GameTable.setLOGGER(gameTableLogger);
//		GameTable gameTable = new GameTable();
//
//		SimpleShipParser shipparser = new SimpleShipParser();
//		PositionGenerator positionGenerator = new PositionGenerator();
//
//		ShipFileReader reader = new ShipFileReader();
//
//		reader.setReader(new BufferedReader(new FileReader("patterns.txt")));
//
//		List<Ship> shipList = reader.readShipsFromFile();
//
//		gameTable.setShipparser(shipparser);
//		gameTable.setRandomPositionGenerator(positionGenerator);
//		gameTable.addAll(shipList);
//
//		gameController.setGameTable(gameTable);
//
//		gameController.keepShootingTillAllShipsGoDown();
//		
//		System.out.println( "Number of shoots fired: " + gameTable.getNumberOfShootsFired() );

	}
}
