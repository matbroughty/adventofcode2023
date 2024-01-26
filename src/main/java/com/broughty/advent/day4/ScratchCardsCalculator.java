package com.broughty.advent.day4;

import com.broughty.advent.common.AdventCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ScratchCardsCalculator extends AdventCalculator {

    List<CardGame> games;

    public ScratchCardsCalculator(@Value("${advent.day4File}") String file) {
        super(file);
    }

    public void process() {
        games = getFileLines().stream().map(CardGame::new).collect(Collectors.toList());
    }


    public List<CardGame> getGames() {
        return games;
    }

    public int calculateOverallPoints() {
        AtomicInteger sum = new AtomicInteger();
        games.stream().filter(cardGame -> cardGame.getCardPoints() != 0).forEach(cardGame -> sum.addAndGet(cardGame.getCardPoints()));
        return sum.get();
    }

    public int calculateScratchCardTotal() {
        Map<CardGame, AtomicInteger> map = new HashMap<>();
        getGames().forEach(card -> map.put(card, new AtomicInteger(1)));
        getGames().forEach(key -> {
                    int wins = key.cardWins();
                    if (wins > 0) {
                        int startIndex = getGames().indexOf(key) + 1;
                        int endIndex = Math.min((startIndex + wins), getGames().size());

                        // for each card of this key we need to work out the other cards it 'won'
                        int currentCardCount = map.get(key).intValue();
                        logger.info("Card {} wins cards at {} to {}", key, startIndex, endIndex);
                        for (int i = 0; i < currentCardCount; i++) {

                            getGames().subList(startIndex, endIndex).forEach(
                                    cardGame -> map.get(cardGame).getAndIncrement()
                            );
                        }
                    }
                }
        );


        return map.values().stream().mapToInt(AtomicInteger::intValue).sum();
    }
}
