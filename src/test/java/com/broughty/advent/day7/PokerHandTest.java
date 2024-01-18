package com.broughty.advent.day7;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandTest {

    @Test
    void parseHand() {
        PokerHand pokerHand = new PokerHand("32T3K", 765, false);
        assertEquals(new BigDecimal("2.0302100313"), pokerHand.parseHand());

        pokerHand = new PokerHand("T55J5", 765, false);
        assertEquals(new BigDecimal("4.1005051105"), pokerHand.parseHand());


        pokerHand = new PokerHand("KTJJT", 765, false);
        assertEquals(new BigDecimal("3.1310111110"), pokerHand.parseHand());

        pokerHand = new PokerHand("AAAAA", 765, false);
        assertEquals(new BigDecimal("7.1414141414"), pokerHand.parseHand());


        pokerHand = new PokerHand("TTAAA", 765, false);
        assertEquals(new BigDecimal("5.1010141414"), pokerHand.parseHand());

        pokerHand = new PokerHand("24TQ8", 243, false);
        assertEquals(new BigDecimal("1.0204101208"), pokerHand.parseHand());

        pokerHand = new PokerHand("TJK79", 857, false);
        assertEquals(new BigDecimal("1.1011130709"), pokerHand.parseHand());


    }

    @Test
    void parseHandJokers() {
        PokerHand pokerHand = new PokerHand("32T3K", 122, true);
        assertEquals(new BigDecimal("2.0302100313"), pokerHand.parseHand());

        // just a single card but a J so we have a pair
        pokerHand = new PokerHand("32TJK", 122, true);
        assertEquals(new BigDecimal("2.0302100113"), pokerHand.parseHand());

        // 2 3s and a J so 3 of a kind
        pokerHand = new PokerHand("J3T3K", 122, true);
        assertEquals(new BigDecimal("4.0103100313"), pokerHand.parseHand());

        // 2 Js so turn to K for three K's
        pokerHand = new PokerHand("JJT3K", 122, true);
        assertEquals(new BigDecimal("4.0101100313"), pokerHand.parseHand());
    }
}