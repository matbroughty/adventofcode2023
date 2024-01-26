package com.broughty.advent.common;

import org.apache.commons.lang3.StringUtils;

public enum AdventDay {

    DAY1,
    DAY2,
    DAY3,
    DAY4,
    DAY5,
    DAY6,
    DAY7,
    DAY8,
    DAY9;


    public String getAdventDayLink(){
        return STR."https://adventofcode.com/2023/day/\{StringUtils.substringAfter(this.name(), "DAY")}";
    }
}
