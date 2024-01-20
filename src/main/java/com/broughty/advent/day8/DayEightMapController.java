package com.broughty.advent.day8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayEightMapController {

    private final MapMovementCalculator mapMovementCalculator;
    Logger logger = LoggerFactory.getLogger(DayEightMapController.class);

    public DayEightMapController(MapMovementCalculator mapMovementCalculator) {
        this.mapMovementCalculator = mapMovementCalculator;
        this.mapMovementCalculator.readFileLines();
    }

    @GetMapping("/day8")
    String getTotal() {
        logger.info("Day Eight - move through map");
        long movesToZZZ = mapMovementCalculator.mapMoveCount();
        return STR."Moves to ZZZ Part One: \{movesToZZZ}";
    }


}
