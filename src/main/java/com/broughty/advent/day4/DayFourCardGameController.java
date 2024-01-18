package com.broughty.advent.day4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayFourCardGameController {

    private final ScratchCardsCalculator scratchCardsCalculator;
    Logger logger = LoggerFactory.getLogger(DayFourCardGameController.class);

    public DayFourCardGameController(ScratchCardsCalculator scratchCardsCalculator) {
        this.scratchCardsCalculator = scratchCardsCalculator;
        this.scratchCardsCalculator.readFileLines();
        this.scratchCardsCalculator.process();
    }

    @GetMapping("/day4")
    String getTotal() {
        logger.info("Day Four Scratch Cards Controller.getTotal() called");
        logger.info("file is {}", scratchCardsCalculator.getFileUrl());
        return "Total points for day4 scratch cards is : " + scratchCardsCalculator.calculateOverallPoints() + " total number of cards = " + scratchCardsCalculator.calculateScratchCardTotal();
    }


}
