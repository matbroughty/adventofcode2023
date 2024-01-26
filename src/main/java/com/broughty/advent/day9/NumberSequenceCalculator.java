package com.broughty.advent.day9;

import com.broughty.advent.common.AdventCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NumberSequenceCalculator extends AdventCalculator {

    public NumberSequenceCalculator(@Value("${advent.day9File}") String file) {
        super(file);

    }
}
