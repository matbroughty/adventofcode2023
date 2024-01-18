package com.broughty.advent.day6;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
class BoatRaceCalculatorTest {

    @Value("${advent.day6File}")
    String fileUrl;
    @Value("${advent.cookie}")
    String cookie;

    private final static String EXAMPLE_RACES = """
            Time:      7  15   30
            Distance:  9  40  200
            """;

    private final static String EXAMPLE_KERNING_RACE = """
            Time:      71530
            Distance:  940200
            """;

    private final static String PART_ONE_RACES = """
            Time:        47     84     74     67
            Distance:   207   1394   1209   1014
            """;

    private final static String PART_TWO_RACES = """
            Time:        47847467
            Distance:   207139412091014
            """;


    @Test
    public void testGetTimeDistancePairs(){
        Reader inputString = new StringReader(EXAMPLE_RACES);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> lines = reader.lines().toList();


        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        boatRaceCalculator.setFileLines(lines);

        assertEquals(3, boatRaceCalculator.getTimeDistancePairs(lines).size());
    }


    @Test
    public void testExampleBoatRacesRecordBeatingTotal(){
        Reader inputString = new StringReader(EXAMPLE_RACES);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> lines = reader.lines().toList();

        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        boatRaceCalculator.setFileLines(lines);
        assertEquals(288,boatRaceCalculator.recordBeatingTotal());
    }

    @Test
    public void testPart2ExampleBoatRacesRecordBeatingTotal(){
        Reader inputString = new StringReader(EXAMPLE_KERNING_RACE);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> lines = reader.lines().toList();

        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        boatRaceCalculator.setFileLines(lines);
        assertEquals(71503,boatRaceCalculator.recordBeatingTotal());
    }

    @Test
    public void testPartOneBoatRacesRecordBeatingTotal(){
        Reader inputString = new StringReader(PART_ONE_RACES);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> lines = reader.lines().toList();

        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        boatRaceCalculator.setFileLines(lines);
        assertEquals(741000,boatRaceCalculator.recordBeatingTotal());
    }

    @Test
    public void testPartTwoBoatRacesRecordBeatingTotal(){
        Reader inputString = new StringReader(PART_TWO_RACES);
        BufferedReader reader = new BufferedReader(inputString);
        List<String> lines = reader.lines().toList();

        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        boatRaceCalculator.setFileLines(lines);
        assertEquals(38220708,boatRaceCalculator.recordBeatingTotal());
    }

    @Test
    public void testCalculateWins(){
        BoatRaceCalculator boatRaceCalculator = new BoatRaceCalculator(fileUrl);
        assertEquals(4,boatRaceCalculator.calculateWins(Pair.of(7L, 9L)));

    }

}