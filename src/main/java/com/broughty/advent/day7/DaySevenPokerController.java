package com.broughty.advent.day7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DaySevenPokerController {

    private final PokerTotalCalculator pokerTotalCalculator;
    Logger logger = LoggerFactory.getLogger(DaySevenPokerController.class);

    public DaySevenPokerController(PokerTotalCalculator pokerTotalCalculator) {
        this.pokerTotalCalculator = pokerTotalCalculator;
        this.pokerTotalCalculator.readFileLines();
    }

    @GetMapping("/day7")
    String getTotal() {
        logger.info("Day Seven Poker Odds");
        long recordBeatingTotal = pokerTotalCalculator.winningTotals(false);
        long totalWithJokers = pokerTotalCalculator.winningTotals(true);
        return STR."Poker Winning Totals Part One: \{recordBeatingTotal} and part Two with Joker: \{totalWithJokers} ";
    }


}
