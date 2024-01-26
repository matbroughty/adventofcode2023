package com.broughty.advent.day7;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DaySevenPlugin implements AdventDayPlugin {

    private final PokerTotalCalculator pokerTotalCalculator;

    public DaySevenPlugin(PokerTotalCalculator pokerTotalCalculator) {
        this.pokerTotalCalculator = pokerTotalCalculator;
        this.pokerTotalCalculator.readFileLines();

    }

    @Override
    public String partOne() {
        return STR."Poker Winning Totals Part One: \{pokerTotalCalculator.winningTotals(false)}";
    }

    @Override
    public String partTwo() {
        return STR."Poker Winning Totals Part Two: \{pokerTotalCalculator.winningTotals(true)}";
    }

    @Override
    public AdventCalculator getCalculator() {
        return pokerTotalCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY7.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY7.getAdventDayLink();
    }
}
