package com.broughty.advent.day2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
class CubeBagCalculatorTest {


    @Value("${advent.day2File}")
    String fileUrl;

    @Value("${advent.cookie}")
    String cookie;

    @Test
    public void testCubeGameTotal() {
        CubeBagCalculator bagCalculator = new CubeBagCalculator(fileUrl);
        bagCalculator.setCookie(cookie);
        bagCalculator.readFileLines();

        assertEquals(2632, bagCalculator.validCubeGameTotals());

    }

    @Test
    public void testCubeGamePowerTotals() {
        CubeBagCalculator bagCalculator = new CubeBagCalculator(fileUrl);
        bagCalculator.setCookie(cookie);
        bagCalculator.readFileLines();

        assertEquals(69629, bagCalculator.totalCubeGamePowerTotals());

    }


    @Test
    public void testCubeBagGameRecord() {
        CubeBagCalculator.CubeBagGame cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        assertEquals(1, cubeBagGameRecord.gameId());
        assertTrue(cubeBagGameRecord.isValid());

        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
        assertEquals(2, cubeBagGameRecord.gameId());
        assertTrue(cubeBagGameRecord.isValid());

        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
        assertEquals(3, cubeBagGameRecord.gameId());
        assertFalse(cubeBagGameRecord.isValid());

        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
        assertEquals(4, cubeBagGameRecord.gameId());
        assertFalse(cubeBagGameRecord.isValid());

        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
        assertEquals(5, cubeBagGameRecord.gameId());
        assertTrue(cubeBagGameRecord.isValid());


        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 25: 8 green, 4 blue; 9 blue, 7 red; 5 green, 15 blue, 11 red; 11 green, 14 red, 10 blue");
        assertEquals(25, cubeBagGameRecord.gameId());
        assertFalse(cubeBagGameRecord.isValid());

        cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 94: 3 red, 14 green; 3 blue, 15 green, 3 red; 2 red, 15 green");
        assertEquals(94, cubeBagGameRecord.gameId());
        assertFalse(cubeBagGameRecord.isValid());

    }

    @Test
    public void testCubeBagGameRecordFewest() {
        CubeBagCalculator.CubeBagGame  cubeBagGameRecord = new CubeBagCalculator.CubeBagGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        assertEquals(1, cubeBagGameRecord.gameId());
        assertEquals(48, cubeBagGameRecord.cubePower());


    }



}