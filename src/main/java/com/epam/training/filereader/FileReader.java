package com.epam.training.filereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.training.torpedo.domain.Ship;

public class FileReader {

	private static final int SIZE_OF_PATTERN = 5;

	BufferedReader reader;

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
		return null;

	}

	private List<String> removeAll(List<String> stringList, int radius) {
		List<String> result = new ArrayList<>();

		for( int i = 0; i < radius; ++i ) {
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
