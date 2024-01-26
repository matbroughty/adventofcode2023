package com.broughty.advent.day2;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayTwoPlugin implements AdventDayPlugin {

    private final CubeBagCalculator cubeBagCalculator;

    public DayTwoPlugin(CubeBagCalculator cubeBagCalculator) {
        this.cubeBagCalculator = cubeBagCalculator;
        this.cubeBagCalculator.readFileLines();
    }

    @Override
    public String partOne() {
        return STR."Total number for day2 cube bag file is : \{cubeBagCalculator.validCubeGameTotals()}";
    }

    @Override
    public String partTwo() {
        return STR."Total number for day2 cube Part Two file is : \{cubeBagCalculator.totalCubeGamePowerTotals()}";
    }

    @Override
    public AdventCalculator getCalculator() {
        return cubeBagCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY2.name().equals(day);
    }


    @Override
    public String getAdventUrl() {
        return AdventDay.DAY2.getAdventDayLink();
    }
}
