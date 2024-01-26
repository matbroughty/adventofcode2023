package com.broughty.advent.day3;

import com.broughty.advent.common.AdventCalculator;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GearRatioPartsCalculator extends AdventCalculator {

    public GearRatioPartsCalculator(@Value("${advent.day3File}") String file) {
        super(file);
    }


    public int calculateSparePartsNumber() {
        int rows = getFileLines().size();
        int columns = getFileLines().getFirst().length();
        logger.info("We have an array of [{}][{}]", rows, columns);

        char[][] parts = new char[rows][columns];
        Map<String, Integer> partNumbers = new HashMap<>();

        populate(parts, getFileLines());

        System.out.print("|");
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                System.out.print(parts[i][j]);
                char value = parts[i][j];
                if (!Character.isDigit(value) && value != '.') {
                    logger.debug("position [{}][{}] has char {}", i, j, value);
                    populatePartNumbers(i, j, parts, partNumbers);
                }
            }
            System.out.println("|");
            System.out.print("|");
        }


        System.out.println("Stripped Array ");

        System.out.print("|");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(parts[i][j]);
            }
            System.out.println("|");
            System.out.print("|");
        }


        // should just be numbers now so add them
        return sum(partNumbers);
    }

    private void populatePartNumbers(int i, int j, char[][] parts, Map<String, Integer> partNumbers) {

        // above and below
        checkAdjacency(i - 1, j, parts, partNumbers);
        checkAdjacency(i + 1, j, parts, partNumbers);
        // left and right
        checkAdjacency(i, j + 1, parts, partNumbers);
        checkAdjacency(i, j - 1, parts, partNumbers);
        //bottom left
        checkAdjacency(i - 1, j - 1, parts, partNumbers);
        //bottom right
        checkAdjacency(i - 1, j + 1, parts, partNumbers);
        //top right
        checkAdjacency(i + 1, j + 1, parts, partNumbers);
        //top left
        checkAdjacency(i + 1, j - 1, parts, partNumbers);

    }

    /**
     * Checks for adjacent number and puts in map keyed by start position of the number.
     *
     * @param row         the row starting from 0
     * @param column      the column starting from 0
     * @param parts       the 2d array we are checking
     * @param partNumbers the map of part numbers we need to sum - keyed on row/column of start of digit so we don't add twice
     */
    private void checkAdjacency(int row, int column, char[][] parts, Map<String, Integer> partNumbers) {
        try {
            if (row >= 0 && row < getFileLines().size() && column >= 0 && column < getFileLines().getFirst().length() && Character.isDigit(parts[row][column])) {
                // we know the column but not the start
                Pair<Integer, Integer> startAndNumber = getNumberAndStart(row, column, parts);
                String key = buildKey(row, Objects.requireNonNull(startAndNumber).getValue());
                // if we already have this number then ignore it
                if (!partNumbers.containsKey(key)) {
                    logger.debug("Putting in number {} at [{}][{}] ", startAndNumber.getRight(), row, startAndNumber.getLeft());
                    partNumbers.put(key, startAndNumber.getRight());
                }
            }

        } catch (ArrayIndexOutOfBoundsException ob) {
            logger.info("can't access [{}][{}] so carrying on", row, column, ob);
        }

    }

    private Pair<Integer, Integer> getNumberAndStart(int i, int j, char[][] parts) {
        int start = j;
        int end = j;
        // work back
        while (start - 1 >= 0 && Character.isDigit(parts[i][start - 1])) {
            start--;
        }
        // work forward
        while (end + 1 < getFileLines().getFirst().length() && Character.isDigit(parts[i][end + 1])) {
            end++;
        }
        logger.debug("pretty sure there is a number between [{}][{}] and [{}][{}]", i, start, i, end);
        StringBuilder number = new StringBuilder();
        for (int numStart = start; numStart <= end; numStart++) {
            number.append(Character.getNumericValue(parts[i][numStart]));
            parts[i][numStart] = '.';
        }

        logger.debug("Is this the number {}", number);
        return Pair.of(j, Integer.parseInt(number.toString()));
    }

    private String buildKey(int i, int j) {
        return STR."[\{i}]:[\{j}]";
    }

    private int sum(Map<String, Integer> partNumbers) {
        AtomicInteger sum = new AtomicInteger();
        partNumbers.keySet().forEach(key -> sum.addAndGet(partNumbers.get(key))
        );

        partNumbers.keySet().stream().sorted().forEach(key ->
                logger.info("At {} we have {}", key, partNumbers.get(key)));
        return sum.get();
    }

    private void populate(char[][] parts, List<String> fileLines) {
        int row = 0;
        for (String line : fileLines) {
            System.arraycopy(line.toCharArray(), 0, parts[row], 0, line.toCharArray().length);
            row++;
        }
    }

}
