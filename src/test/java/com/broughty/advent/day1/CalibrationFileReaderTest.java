package com.broughty.advent.day1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
class CalibrationFileReaderTest {


    @Value("${advent.day1File}")
    String fileUrl;

    @Value("${advent.cookie}")
    String cookie;

    @Test
    void getFileTotalWithoutWords() {


        CalibrationFileReader calibrationFileReader = new CalibrationFileReader(fileUrl);
        calibrationFileReader.setCookie(cookie);
        calibrationFileReader.readFileLines();

        assertEquals(55488, calibrationFileReader.readFileTotal(false));


    }

    @Test
    void getFileTotalWithWords() {


        CalibrationFileReader calibrationFileReader = new CalibrationFileReader(fileUrl);
        calibrationFileReader.setCookie(cookie);
        calibrationFileReader.readFileLines();

        // too high
        assertEquals(55625, calibrationFileReader.readFileTotal(true));


    }



    @Test
    void testLineSum(){

        assertEquals(12, CalibrationFileReader.processLine("1abc2"));
        assertEquals(38, CalibrationFileReader.processLine("pqr3stu8vwx"));
        assertEquals(15, CalibrationFileReader.processLine("a1b2c3d4e5f"));
        assertEquals(77, CalibrationFileReader.processLine("treb7uchet"));
        assertEquals(11, CalibrationFileReader.processLine("two1nine"));

    }


    @Test
    void testLineSumWithWords(){




        assertEquals(48, CalibrationFileReader.processLineWithWords("cjtnzpfourxspsjvmrbqclkzkdftc8pdfbfxjnvflgfkqctvcsc8888"));

        assertEquals(18, CalibrationFileReader.processLineWithWords("onejscsevensix2oneightd"));

        assertEquals(11, CalibrationFileReader.processLineWithWords("onesevenninesvnnrvxxeightrdbvsbdnzgtmlmf1"));

        assertEquals(21, CalibrationFileReader.processLineWithWords("jsgtwonefvmcdsnqfp4fivefivesevenhkbkqcb1vgkshfnxfc"));

        assertEquals(99, CalibrationFileReader.processLineWithWords("9n"));

        assertEquals(68, CalibrationFileReader.processLineWithWords("six4294seven8"));

        assertEquals(71, CalibrationFileReader.processLineWithWords("seveneight74five1"));


        assertEquals(55, CalibrationFileReader.processLineWithWords("fivefivefive"));

        assertEquals(55, CalibrationFileReader.processLineWithWords("five"));

        assertEquals(52, CalibrationFileReader.processLineWithWords("562"));

        assertEquals(65, CalibrationFileReader.processLineWithWords("6two92635"));

        assertEquals(81, CalibrationFileReader.processLineWithWords("8dzszone"));

        assertEquals(66, CalibrationFileReader.processLineWithWords("hfcm6"));

        assertEquals(71, CalibrationFileReader.processLineWithWords("seven4four9eightxqlsixthreeone"));

        assertEquals(14, CalibrationFileReader.processLineWithWords("1ninesix43bmsttggvzn14"));

        assertEquals(76, CalibrationFileReader.processLineWithWords("seven1sixbmnxmz"));

        assertEquals(38, CalibrationFileReader.processLineWithWords("threeseventwoseven87dnmqjbbxfoneighttfz"));

        assertEquals(44, CalibrationFileReader.processLineWithWords("rjsljnqcrttbpfhfxbfourone4"));

        assertEquals(63, CalibrationFileReader.processLineWithWords("sixthreethree5scmqrfq4vf3"));

        assertEquals(54, CalibrationFileReader.processLineWithWords("five4qbvxthrfxdkjkk"));

        assertEquals(83, CalibrationFileReader.processLineWithWords("eightwothree"));

        assertEquals(22, CalibrationFileReader.processLineWithWords("gfrtwo"));

        assertEquals(38, CalibrationFileReader.processLineWithWords("pqr3stu8vwx"));
        assertEquals(88, CalibrationFileReader.processLineWithWords("pq8one8"));


        assertEquals(28, CalibrationFileReader.processLineWithWords("two1nineeight"));
        assertEquals(29, CalibrationFileReader.processLineWithWords("two1nine"));
        assertEquals(21, CalibrationFileReader.processLineWithWords("two1nine1"));

        assertEquals(13, CalibrationFileReader.processLineWithWords("abcone2threexyz"));
        assertEquals(24, CalibrationFileReader.processLineWithWords("xtwone3four"));




        assertEquals(42, CalibrationFileReader.processLineWithWords("4nineeightseven2"));
        assertEquals(14, CalibrationFileReader.processLineWithWords("zoneight234"));
        assertEquals(76, CalibrationFileReader.processLineWithWords("7pqrstsixteen"));

    }

    @Test
    void testLineHasWord(){


        assertTrue( CalibrationFileReader.hasWord("two1nine"));
        assertTrue(CalibrationFileReader.hasWord("eightwothree"));
        assertTrue(CalibrationFileReader.hasWord("abcone2threexyz"));
        assertTrue(CalibrationFileReader.hasWord("xtwone3four"));


        assertTrue( CalibrationFileReader.hasWord("4nineeightseven2"));
        assertTrue(CalibrationFileReader.hasWord("zoneight234"));
        assertTrue(CalibrationFileReader.hasWord("7pqrstsixteen"));

        assertFalse(CalibrationFileReader.hasWord("1abc2"));
        assertFalse(CalibrationFileReader.hasWord(""));

    }

    @Test
    public void testIndexToNumberIsBeforeOrAfterOtherNumeric(){

        CalibrationFileReader.IndexToNumber indexToNumber =
                new CalibrationFileReader.IndexToNumber(3, 3, "One");
        assertTrue(indexToNumber.isBeforeOrAfterOtherNumeric(1, 1));

        assertFalse(indexToNumber.isBeforeOrAfterOtherNumeric(1, 5));



    }

    @Test
    public void testIndexToNumberIsSameNumber(){

        CalibrationFileReader.IndexToNumber indexToNumber =
                new CalibrationFileReader.IndexToNumber(3, 3, "One");
        assertTrue(indexToNumber.isSamePosition());

        indexToNumber =
                new CalibrationFileReader.IndexToNumber(2, 4, "One");
        assertFalse(indexToNumber.isSamePosition());

    }


    @Test
    void wordLineSum() {


        assertEquals(31,
                CalibrationFileReader.wordLineSum("cmmplbcnml36threetrxnhrrdonelmspsbhfd9twonenn",
                        new CalibrationFileReader.FirstAndSecondNumber("3", "9")));

        assertEquals(14,
                CalibrationFileReader.wordLineSum("zoneight234",
                        new CalibrationFileReader.FirstAndSecondNumber("2", "4")));

        assertEquals(76,
                CalibrationFileReader.wordLineSum("7pqrstsixteen",
                        new CalibrationFileReader.FirstAndSecondNumber("7", "7")));






    }
}