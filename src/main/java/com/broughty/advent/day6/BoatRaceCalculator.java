package com.broughty.advent.day6;

import com.broughty.advent.common.AdventCalculator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.trim;

@Component
public class BoatRaceCalculator extends AdventCalculator {


    public BoatRaceCalculator(@Value("${advent.day6File}") String file) {
        super(file);
    }

    public long recordBeatingTotal() {
        List<Pair<Long, Long>> timeDistancePairs = getTimeDistancePairs(getFileLines());
        return calculateCombinedTotal(timeDistancePairs);
    }

    List<Pair<Long, Long>> getTimeDistancePairs(List<String> fileLines) {
        String[] time = StringUtils.substringAfterLast(fileLines.getFirst(), ":").trim().split("\\s+");
        String[] distance = StringUtils.substringAfterLast(fileLines.get(1), ":").trim().split("\\s+");

        List<Pair<Long, Long>> timeDistance = new ArrayList<>();
        for (int i = 0; i < time.length; i++) {
            var pair = Pair.of(Long.parseLong(trim(time[i])), Long.parseLong(trim(distance[i])));
            timeDistance.add(pair);
        }
        return timeDistance;
    }

    private long calculateCombinedTotal(List<Pair<Long, Long>> timeDistancePairs) {
        return timeDistancePairs.stream().map(
                this::calculateWins
        ).reduce(1, Math::multiplyExact);
    }

    Integer calculateWins(Pair<Long, Long> pair) {
        long time = pair.getLeft();
        long bestDistance = pair.getRight();
        long buttonPress = 1;

        int wins = 0;
        while (time - buttonPress > 0) {
            long distance = buttonPress * (time - buttonPress);
            if (distance > bestDistance) {
                wins++;
            }
            buttonPress++;
        }

        return wins;
    }
}
