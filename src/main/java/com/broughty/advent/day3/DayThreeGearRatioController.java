package com.broughty.advent.day3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayThreeGearRatioController {

    private final GearRatioPartsCalculator gearRatioPartsCalculator;
    Logger logger = LoggerFactory.getLogger(DayThreeGearRatioController.class);

    public DayThreeGearRatioController(GearRatioPartsCalculator gearRatioPartsCalculator) {
        this.gearRatioPartsCalculator = gearRatioPartsCalculator;
        this.gearRatioPartsCalculator.readFileLines();
    }

    @GetMapping("/day3")
    String getTotal() {
        logger.info("DayThree Gear Ratio Controller.getTotal() called");
        logger.info("file is {}", gearRatioPartsCalculator.getFileUrl());
        return "Total number for day3 gear ratio file is : " + gearRatioPartsCalculator.calculateSparePartsNumber();
    }


}
