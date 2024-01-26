package com.broughty.advent.common;

import org.springframework.plugin.core.Plugin;

public interface AdventDayPlugin extends Plugin<String> {


    /**
     * Part One of the Advent Day calculation
     */
    String partOne();

    /**
     * Part Two of the Advent Day calculation
     */
    String partTwo();

    /**
     * Returns the calculator instance used by the plugin
     * Future use
     *
     * @return the AdventCalculator implementation
     */
    @SuppressWarnings("unused")
    AdventCalculator getCalculator();

    String getAdventUrl();


}
