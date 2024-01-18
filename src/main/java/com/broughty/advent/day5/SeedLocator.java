package com.broughty.advent.day5;

import com.broughty.advent.common.AdventUrlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeedLocator extends AdventUrlReader {


    public SeedLocator(@Value("${advent.day5File}") String file) {
        super(file);
    }

    public Almanac buildAlmanac() {
        return new Almanac(getFileLines());
    }
}
