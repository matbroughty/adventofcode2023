package com.broughty.advent.day9;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayNinePlugin implements AdventDayPlugin {

    NumberSequenceCalculator numberSequenceCalculator;

    DayNinePlugin(NumberSequenceCalculator numberSequenceCalculator) {
        this.numberSequenceCalculator = numberSequenceCalculator;
    }

    @Override
    public String partOne() {
        return "TODO";
    }

    @Override
    public String partTwo() {
        return "TODO";
    }

    @Override
    public AdventCalculator getCalculator() {
        return numberSequenceCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY9.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY9.getAdventDayLink();
    }
}
