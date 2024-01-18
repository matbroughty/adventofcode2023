package com.broughty.advent.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayTwoCubeBagController {

    private final CubeBagCalculator cubeBagCalculator;
    Logger logger = LoggerFactory.getLogger(DayTwoCubeBagController.class);

    public DayTwoCubeBagController(CubeBagCalculator cubeBagCalculator) {
        this.cubeBagCalculator = cubeBagCalculator;
        this.cubeBagCalculator.readFileLines();
    }

    @GetMapping("/day2")
    String getTotal() {
        logger.info("DayTwoCube Bag Controller.getTotal() called");
        logger.info("file is {}", cubeBagCalculator.getFileUrl());
        return STR."Total number for day2 cube bag file is : \{cubeBagCalculator.validCubeGameTotals()} the part 2 power cubed is \{cubeBagCalculator.totalCubeGamePowerTotals()}";
    }


}
