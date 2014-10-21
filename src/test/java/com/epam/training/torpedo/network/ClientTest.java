package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	private Client underTest;
	private DataOutputStream dataOutputStreamMock;
	private BufferedReader bufferedReaderMock;

	@Before
	public void setUp() {

		dataOutputStreamMock = EasyMock.createMock(DataOutputStream.class);
		bufferedReaderMock = EasyMock.createMock(BufferedReader.class);

		underTest = new Client();

		underTest.setFromServer(bufferedReaderMock);
		underTest.setToServer(dataOutputStreamMock);

	}

	@Test
	public void testSendData() {
		// GIVEN
		String dataToSend = "data";

		try {
			dataOutputStreamMock.writeBytes(dataToSend);
			EasyMock.expectLastCall().once();
		} catch (IOException e) {
			Assert.fail();
		}

		EasyMock.replay(dataOutputStreamMock);
		// WHEN
		underTest.sendData(dataToSend);
		// THEN
		EasyMock.verify(dataOutputStreamMock);

	}
}
