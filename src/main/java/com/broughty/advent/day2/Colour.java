package com.broughty.advent.day2;

public enum Colour {

    red(12),
    green(13),
    blue(14);


    private final int maximum;

    Colour(int maximum) {
        this.maximum = maximum;
    }


    public int getMaximum() {
        return maximum;
    }


}
