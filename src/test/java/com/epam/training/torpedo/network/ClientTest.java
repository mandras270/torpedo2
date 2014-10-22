package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epam.training.torpedo.ai.EasyShooter;
import com.epam.training.torpedo.domain.GameTable;

public class ClientTest {

	private Client underTest;
	private DataOutputStream dataOutputStreamMock;
	private BufferedReader bufferedReaderMock;
	private GameTable gameTableMock;
	private EasyShooter shooterMock;

	@Before
	public void setUp() {

		dataOutputStreamMock = EasyMock.createMock(DataOutputStream.class);
		bufferedReaderMock = EasyMock.createMock(BufferedReader.class);
		gameTableMock = EasyMock.createMock(GameTable.class);
		shooterMock = EasyMock.createMock(EasyShooter.class);

		underTest = new Client();

		underTest.setFromServer(bufferedReaderMock);
		underTest.setToServer(dataOutputStreamMock);
		underTest.setGameTable(gameTableMock);
		underTest.setShooter(shooterMock);

	}

	@Test
	public void testReadData() {
		// GIVEN
		String expected = "data";

		try {

			EasyMock.expect(bufferedReaderMock.readLine()).andReturn(expected).once();

		} catch (IOException e) {

			Assert.fail();
		}
		EasyMock.replay(bufferedReaderMock);

		// WHEN
		String actual = underTest.readData();

		// THEN
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = RuntimeException.class)
	public void testReadDataShouldThrowException() {
		// GIVEN
		try {

			EasyMock.expect(bufferedReaderMock.readLine()).andThrow(new RuntimeException());

		} catch (IOException e) {
			Assert.fail();
		}
		EasyMock.replay(bufferedReaderMock);

		// WHEN
		underTest.readData();

		// THEN EXCEPTION THROWN

	}

	@Test
	public void testInit() {
		// WHEN
		String testWelcomeMessage = "WELCOME 4,9";

		try {

			EasyMock.expect(bufferedReaderMock.readLine()).andReturn(testWelcomeMessage).once();

		} catch (IOException e) {
			Assert.fail();
		}

		gameTableMock.setNumberOfRows(4);
		EasyMock.expectLastCall().once();

		gameTableMock.setNumberOfColumn(9);
		EasyMock.expectLastCall().once();

		EasyMock.expect(gameTableMock.getNumberOfColumn()).andReturn(9).once();
		EasyMock.expect(gameTableMock.getNumberOfRows()).andReturn(4).once();

		shooterMock.setNumberOfColumns(9);
		EasyMock.expectLastCall().once();

		shooterMock.setNumberOfRows(4);
		EasyMock.expectLastCall().once();

		shooterMock.init();
		EasyMock.expectLastCall().once();

		gameTableMock.positionShips();
		EasyMock.expectLastCall().once();

		EasyMock.replay(bufferedReaderMock);
		EasyMock.replay(gameTableMock);
		EasyMock.replay(shooterMock);

		// WHEN
		underTest.init();

		// THEN
		EasyMock.verify(bufferedReaderMock);
		EasyMock.verify(gameTableMock);
		EasyMock.verify(shooterMock);

	}

	// @Test
	// public void testGameController() {
	// // WHEN
	// EasyMock.expect(shooterMock.shoot()).andReturn(EasyMock.anyObject(Position.class));
	//
	// try {
	//
	// dataOutputStreamMock.writeBytes(EasyMock.anyString());
	// EasyMock.expectLastCall().once();
	//
	// } catch (IOException e) {
	// Assert.fail();
	// }
	//
	// try {
	//
	// EasyMock.expect(bufferedReaderMock.readLine()).andReturn(EasyMock.anyString()).once();
	//
	// } catch (IOException e) {
	// Assert.fail();
	// }
	//
	// EasyMock.replay(shooterMock);
	// EasyMock.replay(dataOutputStreamMock);
	// EasyMock.replay(bufferedReaderMock);
	//
	// // WHEN
	// underTest.gameController();
	//
	// // THEN
	// EasyMock.verify(shooterMock);
	// EasyMock.verify(dataOutputStreamMock);
	// EasyMock.verify(bufferedReaderMock);
	// }
}
