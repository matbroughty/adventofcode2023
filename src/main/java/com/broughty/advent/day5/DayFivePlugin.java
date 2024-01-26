package com.broughty.advent.day5;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayFivePlugin implements AdventDayPlugin {

    private final SeedLocator seedLocator;

    public DayFivePlugin(SeedLocator seedLocator) {
        this.seedLocator = seedLocator;
        this.seedLocator.readFileLines();
    }

    @Override
    public String partOne() {
        long lowest = seedLocator.buildAlmanac().lowestNumber(false);
        return STR."Lowest seed to location number : \{lowest}";
    }

    @Override
    public String partTwo() {
        return "Not Implemented";
    }

    @Override
    public AdventCalculator getCalculator() {
        return seedLocator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY5.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY5.getAdventDayLink();
    }
}
