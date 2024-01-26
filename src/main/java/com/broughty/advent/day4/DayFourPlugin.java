package com.broughty.advent.day4;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

@Controller
public class DayFourPlugin implements AdventDayPlugin {

    private final ScratchCardsCalculator scratchCardsCalculator;

    public DayFourPlugin(ScratchCardsCalculator scratchCardsCalculator) {
        this.scratchCardsCalculator = scratchCardsCalculator;
        this.scratchCardsCalculator.readFileLines();
        this.scratchCardsCalculator.process();
    }


    @Override
    public String partOne() {
        return STR."Total points for day4 scratch cards is : \{scratchCardsCalculator.calculateOverallPoints()}";
    }

    @Override
    public String partTwo() {
        return STR."total number of cards Part Two = \{scratchCardsCalculator.calculateScratchCardTotal()}";
    }

    @Override
    public AdventCalculator getCalculator() {
        return scratchCardsCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY4.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY4.getAdventDayLink();
    }
}
