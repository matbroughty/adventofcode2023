package com.broughty.advent.day6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DaySixBoatRaceController {

    private final BoatRaceCalculator boatRaceCalculator;
    Logger logger = LoggerFactory.getLogger(DaySixBoatRaceController.class);

    public DaySixBoatRaceController(BoatRaceCalculator boatRaceCalculator) {
        this.boatRaceCalculator = boatRaceCalculator;
        this.boatRaceCalculator.readFileLines();
    }

    @GetMapping("/day6")
    String getTotal() {
        logger.info("Day Six Boat Race Calculator");
        long recordBeatingTotal = boatRaceCalculator.recordBeatingTotal();
        return STR."Record beating total : \{recordBeatingTotal}";
    }


}
