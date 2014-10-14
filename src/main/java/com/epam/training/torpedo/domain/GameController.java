package com.epam.training.torpedo.domain;

import com.epam.training.torpedo.ai.Shooter;

public class GameController {

	Shooter shooter;
	GameTable gameTable;

	public void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	public void setGameTable(GameTable gameTable) {
		this.gameTable = gameTable;
	}

	public void keepShootingTillAllShipsGoDown() {

		while (gameTable.hasShipsLeft()) {
			Position markedPosition = shooter.shoot();
			System.out.println("Shoot on: " + markedPosition);
			gameTable.shootOnPosition(markedPosition);
			System.out.println(gameTable);
		}
	}

}
