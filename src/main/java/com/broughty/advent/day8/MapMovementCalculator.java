package com.broughty.advent.day8;

import com.broughty.advent.common.AdventCalculator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class MapMovementCalculator extends AdventCalculator {


    ArrayDeque<String> moves;
    Map<String, Pair<String, String>> map;

    Logger logger = LoggerFactory.getLogger(MapMovementCalculator.class);

    public MapMovementCalculator(@Value("${advent.day8File}") String file) {
        super(file);
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }

    public void initData() {
        moves = readMoves();
        map = loadMap();
    }

    public long mapMoveCountGhost() {
        initData();
        List<String> elements = map.keySet().stream().filter(str -> StringUtils.endsWith(str, "A")).toList();
        long[] routes = elements.stream().map(e -> moveCount(e, "Z")).mapToLong(Integer::longValue).toArray();
        return lcm(routes);
    }

    public int mapMoveCount() {
        initData();
        return moveCount("AAA", "ZZZ");

    }

    private int moveCount(String startElement, String endElement) {
        boolean found = false;
        int moveCount = 0;

        while (!found) {
            // refill if empty
            if (moves.isEmpty()) {
                moves = readMoves();
            }
            moveCount++;
            startElement = move(Objects.requireNonNull(moves.poll()), startElement, map);
            found = endElement.length() > 1 ? startElement.equals(endElement) : startElement.endsWith(endElement);
        }
        return moveCount;
    }

    private String move(String movement, String element, Map<String, Pair<String, String>> map) {
        return movement.equals("L") ? map.get(element).getLeft() : map.get(element).getRight();
    }

    Map<String, Pair<String, String>> loadMap() {
        Map<String, Pair<String, String>> map = new HashMap<>();
        getFileLines().subList(2, getFileLines().size()).forEach(str -> {
                    String leftRight = trim(remove(remove(substringAfter(str, "="), ")"), "("));
                    logger.debug("left right string = {}", leftRight);
                    map.put(trim(substringBefore(str, "=")),
                            Pair.of(trim(substringBefore(leftRight, ",")),
                                    trim(substringAfter(leftRight, ","))));
                }
        );
        return map;
    }

    ArrayDeque<String> readMoves() {
        ArrayDeque<String> moves = new ArrayDeque<>();
        getFileLines().getFirst().chars().mapToObj(c -> (char) c).forEach(character -> moves.offer(character.toString()));
        return moves;
    }
}
