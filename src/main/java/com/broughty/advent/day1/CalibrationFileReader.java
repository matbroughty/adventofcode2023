package com.broughty.advent.day1;


import com.broughty.advent.common.AdventCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public class CalibrationFileReader extends AdventCalculator {


    CalibrationFileReader(@Value("${advent.day1File}") String file) {
        super(file);
    }

    static int processLine(String line) {
        FirstAndSecondNumber numbers = getNumbers(line);
        return Integer.parseInt(numbers.firstNumber() + numbers.secondNumber());
    }

    static FirstAndSecondNumber getNumbers(String line) {
        String numbers = line.replaceAll("\\D", "");
        logger.debug(numbers);
        if (numbers.isEmpty()) {
            return new FirstAndSecondNumber("-1", "-1");
        }
        String first = String.valueOf(numbers.toCharArray()[0]);
        String second = String.valueOf(numbers.toCharArray()[numbers.length() - 1]);
        logger.debug("first is {} second is {} sum is {}", first, second, first + second);
        return new FirstAndSecondNumber(first, second);
    }

    public static int processLineWithWords(String lineWithWords) {
        FirstAndSecondNumber firstAndSecondNumber = getNumbers(lineWithWords);
        if (hasWord(lineWithWords)) {
            return wordLineSum(lineWithWords, firstAndSecondNumber);
        }
        return processLine(lineWithWords);
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    static int wordLineSum(String lineWithWords, FirstAndSecondNumber firstAndSecondNumber) {
        int firstIndex = lineWithWords.indexOf(firstAndSecondNumber.firstNumber);
        int secondIndex = lineWithWords.lastIndexOf(firstAndSecondNumber.secondNumber);

        List<IndexToNumber> wordNums =
                Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
                        .filter(lineWithWords::contains).map(word -> new IndexToNumber(lineWithWords.indexOf(word), lineWithWords.lastIndexOf(word), word))
                        .filter(s -> firstAndSecondNumber.isJustWords() || s.isBeforeOrAfterOtherNumeric(firstIndex, secondIndex))
                        .map(s -> sanityCheck(s, firstIndex, secondIndex))
                        .sorted()
                        .toList();


        if (!wordNums.isEmpty()) {
            // ok we have a word before or after the numbers
            logger.info("the line '{}' contains words either before {} or after {}", lineWithWords, firstIndex, secondIndex);
            wordNums.forEach(s -> logger.info("word {} is at index {} and last index {}", s.number(), s.firstIndex(), s.lastIndex()));


            IndexToNumber firstWord = wordNums.getFirst();


            IndexToNumber secondWord;
            if (wordNums.size() > 1) {
                secondWord = wordNums.get(wordNums.size() - 1);

                // two words after the last numeric so just take the last one
                if (!firstAndSecondNumber.isJustWords() && firstWord.lastIndex > secondIndex && secondWord.lastIndex > secondIndex) {
                    firstWord = secondWord;
                } else {
                    if (secondWord.lastIndex > secondIndex) {
                        // just words so get both
                        return Integer.parseInt(firstWord.getNumber() + secondWord.getNumber());
                    }
                }
            }

            // we just have a single word number so return it twice
            if (firstAndSecondNumber.isJustWords()) {
                return Integer.parseInt(firstWord.getNumber() + firstWord.getNumber());
            }

            if (firstWord.firstIndex < firstIndex) {
                // word is before the first number
                return Integer.parseInt(firstWord.getNumber() + firstAndSecondNumber.secondNumber);
            } else {
                // word is after the first number
                return Integer.parseInt(firstAndSecondNumber.firstNumber + firstWord.getNumber());
            }

        }

        // just use the numerics
        return processLine(lineWithWords);
    }

    /**
     * If multiple occurrences we only want the first or last one
     */
    private static IndexToNumber sanityCheck(IndexToNumber s, int firstIndex, int secondIndex) {
        if (s.isSamePosition()) {
            return s;
        }
        if (s.firstIndex() < firstIndex) {
            return new IndexToNumber(s.firstIndex, s.firstIndex, s.number());
        }

        if (s.lastIndex() > secondIndex) {
            return new IndexToNumber(s.lastIndex, s.lastIndex, s.number());
        }
        return s;
    }

    static boolean hasWord(String lineWithWords) {
        return Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").anyMatch(lineWithWords::contains);
    }

    public int readFileTotal(boolean withWords) {

        AtomicInteger sum = new AtomicInteger();
        getFileLines().forEach(
                line -> {
                    if (withWords) {
                        sum.addAndGet(processLineWithWords(line));
                    } else {
                        sum.addAndGet(processLine(line));
                    }
                }
        );

        return sum.get();
    }

    record IndexToNumber(int firstIndex, int lastIndex, String number) implements Comparable<IndexToNumber> {

        boolean isBeforeOrAfterOtherNumeric(int numericFirstIndex, int numericLastIndex) {
            return isSamePosition() ? (firstIndex < numericFirstIndex || firstIndex > numericLastIndex) :
                    firstIndex < numericFirstIndex || lastIndex > numericLastIndex;
        }

        boolean isSamePosition() {
            return firstIndex == lastIndex;
        }

        int indexSum() {
            return firstIndex + lastIndex;
        }

        String getNumber() {

            switch (number) {
                case "one" -> {
                    return "1";
                }
                case "two" -> {
                    return "2";
                }
                case "three" -> {
                    return "3";
                }
                case "four" -> {
                    return "4";
                }
                case "five" -> {
                    return "5";
                }
                case "six" -> {
                    return "6";
                }
                case "seven" -> {
                    return "7";
                }
                case "eight" -> {
                    return "8";
                }
                case "nine" -> {
                    return "9";
                }
            }
            return number;
        }

        @Override
        public int compareTo(IndexToNumber o) {
            return indexSum() - o.indexSum();
        }
    }

    record FirstAndSecondNumber(String firstNumber, String secondNumber) {

        boolean isJustWords() {
            return firstNumber.equals("-1");
        }

    }

}
