package com.broughty.advent.day1;

import com.broughty.advent.common.AdventCalculator;
import com.broughty.advent.common.AdventDay;
import com.broughty.advent.common.AdventDayPlugin;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DayOnePlugin implements AdventDayPlugin {

    private final CalibrationFileReader calibrationFileReader;

    public DayOnePlugin(CalibrationFileReader calibrationFileReader) {
        this.calibrationFileReader = calibrationFileReader;
        this.calibrationFileReader.readFileLines();
    }

    @Override
    public String partOne() {
        return STR."Total number for day pt 1 calibration file is : \{calibrationFileReader.readFileTotal(false)}";
    }

    @Override
    public String partTwo() {
        return STR."Total number for day pt 2 calibration file (with words) is : \{calibrationFileReader.readFileTotal(true)}";
    }

    @Override
    public AdventCalculator getCalculator() {
        return calibrationFileReader;
    }

    @Override
    public boolean supports(@NonNull String day) {
        return AdventDay.DAY1.name().equals(day);
    }

    @Override
    public String getAdventUrl() {
        return AdventDay.DAY1.getAdventDayLink();
    }
}
