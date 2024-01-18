package com.broughty.advent.day5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayFiveSeedAlmanacController {

    private final SeedLocator seedLocator;
    Logger logger = LoggerFactory.getLogger(DayFiveSeedAlmanacController.class);

    public DayFiveSeedAlmanacController(SeedLocator seedLocator) {
        this.seedLocator = seedLocator;
        this.seedLocator.readFileLines();
    }

    @GetMapping("/day5")
    String getTotal() {
        logger.info("Day Five Seed Almanac  called");
        long lowest = seedLocator.buildAlmanac().lowestNumber(false);
        return "Lowest seed to location number : " + lowest;
    }


}
