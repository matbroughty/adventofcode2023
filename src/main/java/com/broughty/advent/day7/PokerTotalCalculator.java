package com.broughty.advent.day7;

import com.broughty.advent.common.AdventUrlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Component
public class PokerTotalCalculator extends AdventUrlReader {

    public PokerTotalCalculator(@Value("${advent.day7File}") String file) {
        super(file);
    }

    public long winningTotals(boolean useJoker) {
        List<PokerHand> hands = parsePokerHands(getFileLines(), useJoker);
        Collections.sort(hands);
        return IntStream.range(0, hands.size()).mapToLong(rank -> {
            PokerHand pokerHand = hands.get(rank);
            return pokerHand.totalWinnings() * (rank + 1);
        }).sum();
    }

    List<PokerHand> parsePokerHands(List<String> fileLines, boolean useJoker) {

        return fileLines.stream()
                .map(line ->
                        new PokerHand(substringBefore(line, " "),
                                Integer.parseInt(substringAfter(line, " ")), useJoker))
                .collect(Collectors.toList());

    }
}
