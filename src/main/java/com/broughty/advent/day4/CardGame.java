package com.broughty.advent.day4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.commons.lang3.StringUtils.*;

public record CardGame(String cardGameLine, int cardWins) {

    public static final Logger logger = LoggerFactory.getLogger(CardGame.class);

    public CardGame(String cardGameLine) {
        this(cardGameLine, calculateWins(cardGameLine));
    }

    private static int calculateWins(String cardGameLine) {
        String winningNumbers = trim(substringBefore(substringAfterLast(cardGameLine, ":"), "|"));
        String selectedNumbers = trim(substringAfterLast(substringAfterLast(cardGameLine, ":"), "|"));
        logger.debug("processing winning numbers {} and selected numbers {}", winningNumbers, selectedNumbers);


        List<Integer> winners = new ArrayList<>();
        Arrays.stream(split(winningNumbers)).forEach(num -> {
                    int winningNumInt = Integer.parseInt(num);
                    logger.debug("winning number as string {} and as int {}", num, winningNumInt);
                    winners.add(winningNumInt);
                }
        );

        AtomicInteger wins = new AtomicInteger(0);


        logger.debug("selected numbers length is {}", split(selectedNumbers).length);

        Arrays.stream(split(selectedNumbers)).forEach(num -> {
            if (winners.contains(Integer.parseInt(num))) {
                wins.getAndIncrement();
            }
        });

        return wins.get();
    }


    int getCardPoints() {
        if (cardWins <= 2) {
            return cardWins;
        }
        return Double.valueOf(Math.pow(2, (cardWins - 1))).intValue();

    }
}
