package com.broughty.advent.day1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayOneSnowController {


    private final CalibrationFileReader calibrationFileReader;
    Logger logger = LoggerFactory.getLogger(DayOneSnowController.class);


    public DayOneSnowController(CalibrationFileReader calibrationFileReader) {
        this.calibrationFileReader = calibrationFileReader;
        this.calibrationFileReader.readFileLines();
    }


    @GetMapping("/day1")
    String getTotal() {
        logger.info("DayOneSnowController.getTotal() called");
        logger.info("file is {}", calibrationFileReader.getFileUrl());
        return "Total number for day pt 1 calibration file is : " + calibrationFileReader.readFileTotal(false) + " part 2 = " + calibrationFileReader.readFileTotal(true);
    }


}
