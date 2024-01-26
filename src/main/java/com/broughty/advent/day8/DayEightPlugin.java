package com.broughty.advent.day8;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayEightPlugin implements AdventDayPlugin {

    MapMovementCalculator mapMovementCalculator;

    public DayEightPlugin(MapMovementCalculator mapMovementCalculator) {
        this.mapMovementCalculator = mapMovementCalculator;
        this.mapMovementCalculator.readFileLines();
    }

    @Override
    public String partOne() {
        return STR."Moves to ZZZ Part One: \{mapMovementCalculator.mapMoveCount()}";
    }

    @Override
    public String partTwo() {
        return STR."Moves to ZZZ Part Two (Ghost): \{mapMovementCalculator.mapMoveCountGhost()}";
    }

    @Override
    public AdventCalculator getCalculator() {
        return mapMovementCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY8.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY8.getAdventDayLink();
    }
}
