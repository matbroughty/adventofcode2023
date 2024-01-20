package com.broughty.advent.day8;

import com.broughty.advent.common.AdventUrlReader;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class MapMovementCalculator extends AdventUrlReader {


    ArrayDeque<String> moves;
    Map<String, Pair<String, String>> map;

    Logger logger = LoggerFactory.getLogger(MapMovementCalculator.class);

    public MapMovementCalculator(@Value("${advent.day8File}") String file) {
        super(file);
    }


    public void initData() {
        moves = readMoves();
        map = loadMap();
    }

    public int mapMoveCountGhost() {
        initData();
        return 0;
    }

    public int mapMoveCount() {

        initData();
        boolean foundZZZ = false;
        int moveCount = 0;
        String element = "AAA";
        while (!foundZZZ) {
            // refill if empty
            if (moves.isEmpty()) {
                moves = readMoves();
            }
            moveCount++;
            element = move(Objects.requireNonNull(moves.poll()), element, map);
            foundZZZ = element.equals("ZZZ");
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
                    logger.info("left right string = {}", leftRight);
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
