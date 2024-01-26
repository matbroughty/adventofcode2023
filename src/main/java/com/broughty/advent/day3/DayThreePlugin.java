package com.broughty.advent.day3;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayThreePlugin implements AdventDayPlugin {


    private final GearRatioPartsCalculator gearRatioPartsCalculator;

    public DayThreePlugin(GearRatioPartsCalculator gearRatioPartsCalculator) {
        this.gearRatioPartsCalculator = gearRatioPartsCalculator;
        this.gearRatioPartsCalculator.readFileLines();
    }

    @Override
    public String partOne() {
        return STR."Total number for day3 gear ratio file is : \{gearRatioPartsCalculator.calculateSparePartsNumber()}";
    }

    @Override
    public String partTwo() {
        return "Not Implemented Yet";
    }

    @Override
    public AdventCalculator getCalculator() {
        return gearRatioPartsCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY3.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY3.getAdventDayLink();
    }
}
