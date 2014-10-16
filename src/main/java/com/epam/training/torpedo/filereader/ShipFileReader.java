package com.epam.training.torpedo.filereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.training.torpedo.domain.Ship;

public class ShipFileReader {

	private static final int SIZE_OF_PATTERN = 5;

	BufferedReader reader;

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public List<Ship> readShipsFromFile() {

		List<String> lines = readAllLineFromFile();

		validateInputLines(lines);

		List<Ship> result = parseStringList(lines);

		return result;
	}

	private List<Ship> parseStringList(List<String> lines) {
		List<Ship> result = new ArrayList<>();

		while (!lines.isEmpty()) {
			List<String> oneShip = removeAll(lines, SIZE_OF_PATTERN);
			List<Ship> subShipList = parseShip(oneShip);
			result.addAll(subShipList);
		}

		return result;
	}

	private List<Ship> parseShip(List<String> oneShip) {

		int shipPatternSize = SIZE_OF_PATTERN - 1;

		int[][] shipPattern = new int[shipPatternSize][];

		for (int i = 0; i < shipPatternSize; ++i) {
			String line = oneShip.get(i);
			shipPattern[i] = splitShipParts(line);
		}

		int occurrence = Integer.valueOf(oneShip.get(shipPatternSize));

		return createShipList(shipPattern, occurrence);
	}

	private List<Ship> createShipList(int[][] pattern, int occurrence) {

		List<Ship> result = new ArrayList<>();

		for (int i = 0; i < occurrence; ++i) {
			
			Ship ship = new Ship();
			
			ship.setPattern(pattern);
			
			result.add(ship);
		}

		return result;
	}

	private int[] splitShipParts(String line) {

		int[] result = new int[SIZE_OF_PATTERN - 1];

		String[] parts = line.split(" ");

		validateSize(result.length, parts.length);

		for (int i = 0; i < result.length; ++i) {
			int value = Integer.valueOf(parts[i]);
			result[i] = value;
		}
		return result;
	}

	private void validateSize(int a, int b) {
		if (a != b) {
			throw new IllegalArgumentException("Parse error!");
		}
	}

	private List<String> removeAll(List<String> stringList, int radius) {
		List<String> result = new ArrayList<>();

		for (int i = 0; i < radius; ++i) {
			String first = stringList.remove(0);
			result.add(first);
		}

		return result;
	}

	private void validateReaderIsNotNull() {
		if (reader == null) {
			throw new IllegalArgumentException("The reader cannot be null!");
		}
	}

	private void validateInputLines(List<String> lines) {
		if (lines.size() % SIZE_OF_PATTERN != 0) {
			throw new IllegalArgumentException("The imput from the file was invalid!");
		}
	}

	private List<String> readAllLineFromFile() {
		validateReaderIsNotNull();
		List<String> result = readFile();
		return result;
	}

	private List<String> readFile() {

		List<String> result = new ArrayList<>();
		String line;

		try {

			while ((line = reader.readLine()) != null) {
				result.add(line);
			}

			return result;

		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

	}
}
