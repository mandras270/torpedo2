package com.epam.training.torpedo.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import com.epam.training.torpedo.domain.Position;
import com.epam.training.torpedo.domain.Ship;
import com.epam.training.torpedo.parser.RawPositionParser;

@Configuration
public class AppConfigShip {

	private static final int PATTERN_SIZE = 5;

	@Autowired
	RawPositionParser rawPositionParser;

	@Bean
	@Scope("prototype")
	Ship ship() {
		return new Ship();
	}

	@Bean
	public List<Ship> rawShips() {

		List<Ship> rawShips = new ArrayList<>();

		List<String> patternData = getPatternData();

		Map<Integer[][], Integer> shipPatterns = getShipPatterns(patternData);

		for (Map.Entry<Integer[][], Integer> entry : shipPatterns.entrySet()) {

			for (int i = 0; i < entry.getValue(); ++i) {

				Ship ship = ship();
				List<Position> rawPositions = rawPositionParser.parse(entry.getKey());
				ship.addRawPositions(rawPositions);
				rawShips.add(ship);
			}

		}

		return rawShips;
	}

	@Value("classpath:patterns.txt")
	private Resource patternData;

	private List<String> getPatternData() {

		try (InputStream is = patternData.getInputStream()) {

			return IOUtils.readLines(is);

		} catch (IOException e) {

			throw new RuntimeException("Error while reading pattern file!", e);
		}
	}

	private Map<Integer[][], Integer> getShipPatterns(List<String> patternData) {

		validatePatternDataLength(patternData);

		Map<Integer[][], Integer> shipPatterns = new HashMap<>();

		for (int i = 0; i < patternData.size(); i += PATTERN_SIZE) {
			List<String> patterDataOfOneShip = patternData.subList(i, i + PATTERN_SIZE);
			Map<Integer[][], Integer> parsedPatternData = parseShipData(patterDataOfOneShip);
			shipPatterns.putAll(parsedPatternData);
		}

		return shipPatterns;
	}

	private Map<Integer[][], Integer> parseShipData(List<String> patterDataOfOneShip) {

		Map<Integer[][], Integer> result = new HashMap<>();

		Integer[][] pattern = new Integer[PATTERN_SIZE - 1][PATTERN_SIZE - 1];

		for (int i = 0; i < patterDataOfOneShip.size() - 1; ++i) {

			String[] rawPatternData = patterDataOfOneShip.get(i).split(" ");

			for (int k = 0; k < rawPatternData.length; ++k) {
				pattern[i][k] = Integer.valueOf(rawPatternData[k]);
			}

			String rawOccurrence = patterDataOfOneShip.get(PATTERN_SIZE - 1);
			Integer occurrence = Integer.valueOf(rawOccurrence);

			result.put(pattern, occurrence);
		}

		return result;
	}

	private void validatePatternDataLength(List<String> patternData) {
		if (patternData.size() % PATTERN_SIZE != 0) {
			throw new IllegalArgumentException(" The pattern data is invalid!");
		}
	}
}
