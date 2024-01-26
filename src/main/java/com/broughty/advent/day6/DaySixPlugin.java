package com.broughty.advent.day6;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;


@Controller
public class DaySixPlugin implements AdventDayPlugin {


    private final BoatRaceCalculator boatRaceCalculator;

    public DaySixPlugin(BoatRaceCalculator boatRaceCalculator) {
        this.boatRaceCalculator = boatRaceCalculator;
        this.boatRaceCalculator.readFileLines();
    }

    @Override
    public String partOne() {
        long recordBeatingTotal = boatRaceCalculator.recordBeatingTotal();
        return STR."Record beating total : \{recordBeatingTotal}";
    }

    @Override
    public String partTwo() {
        return "Only implemented in BoatRaceCalculatorTest::testPartTwoBoatRacesRecordBeatingTotal";
    }

    @Override
    public AdventCalculator getCalculator() {
        return boatRaceCalculator;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY6.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY6.getAdventDayLink();
    }
}
