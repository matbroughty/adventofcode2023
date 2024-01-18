package com.broughty.advent.day7;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public record PokerHand(String cards, Integer bet, boolean useJoker) implements Comparable<PokerHand> {
    public long totalWinnings() {
        return bet;
    }

    @Override
    public int compareTo(PokerHand o) {
        return this.handValue().compareTo(o.handValue());
    }


    /**
     * @see #parseHand()
     */
    private BigDecimal handValue() {
        return parseHand();
    }

    BigDecimal parseHand() {

        Map<Integer, Integer> cardCountMap = new HashMap<>();
        AtomicReference<String> handDecimalValue = new AtomicReference<>("");
        AtomicInteger jokerCount = new AtomicInteger(0);
        cards.chars().mapToObj(m -> (char) m).forEach(
                card -> {
                    int cardIntValue = convertCard(card);
                    if (cardIntValue == 1) {
                        jokerCount.getAndIncrement();
                    }
                    handDecimalValue.updateAndGet(v -> v + StringUtils.leftPad(STR."\{cardIntValue}", 2, "0"));
                    cardCountMap.putIfAbsent(cardIntValue, 0);
                    cardCountMap.put(cardIntValue, cardCountMap.get(cardIntValue) + 1);
                }
        );

        String handValueWholeNumber;

        boolean handUsesJoker = useJoker && jokerCount.get() > 0;


        switch (cardCountMap.size()) {
            case 1:
                handValueWholeNumber = "7"; // five of a kind
                break;
            case 2:
                // five of a kind as we either had 2 or 3 J's
                if (cardCountMap.containsValue(4)) {
                    handValueWholeNumber = "6";  // four of a kind
                } else {
                    handValueWholeNumber = "5";  // full house
                }
                if (handUsesJoker) {
                    handValueWholeNumber = "7"; // five of a kind as the one card was a J
                }
                break;
            case 3:
                if (cardCountMap.containsValue(3)) {
                    handValueWholeNumber = "4"; // three of a kind
                    if (handUsesJoker) {
                        handValueWholeNumber = "6"; //
                    }
                } else {
                    handValueWholeNumber = "3"; //two pairs
                    if (handUsesJoker) {
                        // one of the pairs was a JJ so convert to other pair for a 4
                        if (jokerCount.get() == 2) {
                            handValueWholeNumber = "6";
                        } else {
                            // single J so turn one of the pairs to a triple
                            handValueWholeNumber = "5";  // full house
                        }
                    }
                }
                break;
            case 4:
                handValueWholeNumber = "2"; // one pair
                if (handUsesJoker) {
                    handValueWholeNumber = "4"; // single J makes the pair a three
                }
                break;
            case 5:
                handValueWholeNumber = "1"; // no pair - high card
                if (handUsesJoker) {
                    handValueWholeNumber = "2"; // one pair now as the J means we must have a pair
                }
                break;
            default:
                throw new IllegalStateException(STR."Unexpected card count value: \{cardCountMap.size()}");
        }


        return new BigDecimal(STR."\{handValueWholeNumber}.\{handDecimalValue}");
    }

    private int convertCard(Character card) {
        if (Character.isDigit(card)) {
            return Character.getNumericValue(card);
        }

        int cardValue = 0;
        switch (card) {
            case 'T' -> cardValue = 10;
            case 'J' -> cardValue = useJoker ? 1 : 11;
            case 'Q' -> cardValue = 12;
            case 'K' -> cardValue = 13;
            case 'A' -> cardValue = 14;
        }

        return cardValue;

    }

}
