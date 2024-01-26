package com.broughty.advent.day2;


import com.broughty.advent.common.AdventCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.broughty.advent.day2.Colour.*;
import static org.apache.commons.lang3.StringUtils.*;

@Component
public class CubeBagCalculator extends AdventCalculator {

    CubeBagCalculator(@Value("${advent.day2File}") String file) {
        super(file);
    }


    public int validCubeGameTotals() {

        return getFileLines().stream().map(CubeBagGame::new)
                .filter(CubeBagGame::isValid)
                .mapToInt(CubeBagGame::gameId).sum();


    }

    public int totalCubeGamePowerTotals() {

        return getFileLines().stream().map(CubeBagGame::new)
                .mapToInt(CubeBagGame::cubePower).sum();


    }

    record CubeBagGame(String gameLine, int gameId, boolean isValid, int cubePower) {


        public CubeBagGame(String gameLine) {
            this(gameLine, calculateGameId(gameLine), isValidSetOfGames(gameLine), calculateCubePower(gameLine));
        }

        /**
         * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
         * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
         * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
         * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
         * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
         * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
         * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
         * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
         */
        private static boolean isValidSetOfGames(String gameLine) {

            logger.debug("parsing line {}", gameLine);
            AtomicBoolean valid = new AtomicBoolean(true);

            Arrays.stream(substringAfterLast(gameLine, ":").split(";")).flatMap(
                    game -> Arrays.stream(game.split(","))
            ).forEach(
                    colour -> {
                        logger.debug("working with colour {}", substringAfterLast(colour, " "));
                        Colour colourEnum = Colour.valueOf(trim(substringAfterLast(colour, " ")));
                        logger.debug(" enum is {}", colourEnum);
                        switch (colourEnum) {
                            case red:
                                if (parseColourCount(colour) > red.getMaximum()) {
                                    logger.debug("colour {} exceeded for {}", colour, gameLine);
                                    valid.set(false);
                                }
                                break;
                            case blue:
                                if (parseColourCount(colour) > blue.getMaximum()) {
                                    logger.debug("colour {} exceeded for {}", colour, gameLine);
                                    valid.set(false);

                                }
                                break;
                            case green:
                                if (parseColourCount(colour) > green.getMaximum()) {
                                    logger.debug("colour {} exceeded for {}", colour, gameLine);
                                    valid.set(false);
                                }
                                break;
                            default:
                                logger.error("wtf with {}", Colour.valueOf(trim(substringAfterLast(colour, " "))));
                        }
                    }
            );

            return valid.get();
        }

        /**
         * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
         * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
         * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
         * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
         * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
         * In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.
         * Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
         * Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
         * Game 4 required at least 14 red, 3 green, and 15 blue cubes.
         * Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
         * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
         */
        private static int calculateCubePower(String gameLine) {


            final int[] redCount = {0};
            final int[] blueCount = {0};
            final int[] greenCount = {0};


            Arrays.stream(substringAfterLast(gameLine, ":").split(";")).flatMap(
                    game -> Arrays.stream(game.split(","))
            ).forEach(
                    colour -> {
                        logger.debug("working with colour {}", substringAfterLast(colour, " "));
                        Colour colourEnum = Colour.valueOf(trim(substringAfterLast(colour, " ")));
                        logger.debug(" enum is {}", colourEnum);
                        switch (colourEnum) {
                            case red:
                                int count = parseColourCount(colour);
                                if (count > redCount[0]) {
                                    redCount[0] = count;

                                }
                                break;
                            case blue:
                                if (parseColourCount(colour) > blueCount[0]) {
                                    blueCount[0] = parseColourCount(colour);

                                }
                                break;
                            case green:
                                if (parseColourCount(colour) > greenCount[0]) {
                                    greenCount[0] = parseColourCount(colour);
                                }
                                break;
                            default:
                                logger.error("wtf with {}", Colour.valueOf(trim(substringAfterLast(colour, " "))));
                        }
                    }
            );

            return redCount[0] * blueCount[0] * greenCount[0];
        }

        private static int parseColourCount(String colour) {
            return Integer.parseInt(trim(substringBeforeLast(colour, " ")));
        }

        private static int calculateGameId(String gameLine) {
            return Integer.parseInt(substringAfterLast(substringBefore(gameLine, ":"), " "));
        }


    }
}
