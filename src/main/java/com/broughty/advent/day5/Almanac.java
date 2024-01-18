package com.broughty.advent.day5;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.StringUtils.*;

public record Almanac(List<String> rows, long[] seeds, Map<String, List<Triple<Long, Long, Long>>> maps) {

    public static final Logger logger = LoggerFactory.getLogger(Almanac.class);
    public static final String SEED_TO_SOIL = "seed-to-soil map:";
    public static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer map:";

    public static final String FERTILIZER_TO_WATER = "fertilizer-to-water map:";

    public static final String WATER_TO_LIGHT = "water-to-light map:";

    public static final String LIGHT_TO_TEMP = "light-to-temperature map:";

    public static final String TEMP_TO_HUMIDITY = "temperature-to-humidity map:";

    public static final String HUMIDITY_TO_LOCATION = "humidity-to-location map:";

    public Almanac(List<String> rows) {
        this(rows, retrieveSeeds(rows), buildMaps(rows));
    }

    private static Map<String, List<Triple<Long, Long, Long>>> buildMaps(List<String> rows) {

        Map<String, List<Triple<Long, Long, Long>>> map = new HashMap<>();
        map.put(SEED_TO_SOIL, new ArrayList<>());
        map.put(SOIL_TO_FERTILIZER, new ArrayList<>());
        map.put(FERTILIZER_TO_WATER, new ArrayList<>());
        map.put(WATER_TO_LIGHT, new ArrayList<>());
        map.put(LIGHT_TO_TEMP, new ArrayList<>());
        map.put(TEMP_TO_HUMIDITY, new ArrayList<>());
        map.put(HUMIDITY_TO_LOCATION, new ArrayList<>());

        AtomicReference<String> currentMapKey = new AtomicReference<>(SEED_TO_SOIL);
        rows.subList(2, rows.size()).forEach(row -> {
            boolean isMapIndicator = true;
            switch (trim(row)) {
                case SEED_TO_SOIL -> currentMapKey.set(SEED_TO_SOIL);
                case SOIL_TO_FERTILIZER -> currentMapKey.set(SOIL_TO_FERTILIZER);
                case FERTILIZER_TO_WATER -> currentMapKey.set(FERTILIZER_TO_WATER);
                case WATER_TO_LIGHT -> currentMapKey.set(WATER_TO_LIGHT);
                case LIGHT_TO_TEMP -> currentMapKey.set(LIGHT_TO_TEMP);
                case TEMP_TO_HUMIDITY -> currentMapKey.set(TEMP_TO_HUMIDITY);
                case HUMIDITY_TO_LOCATION -> currentMapKey.set(HUMIDITY_TO_LOCATION);
                default -> isMapIndicator = false;
            }

            if (!isMapIndicator && !isBlank(row)) {
                logger.debug("row is {} and map key is {}", row, currentMapKey.get());
                map.get(currentMapKey.get()).add(buildTriple(row));
            }
        });

        return map;
    }

    private static Triple<Long, Long, Long> buildTriple(String row) {
        String[] values = split(row);
        return Triple.of(parseLong(values[0]), parseLong(values[1]), parseLong(values[2]));
    }

    private static long[] retrieveSeeds(List<String> rows) {
        return Arrays.stream(split(substringAfter(rows.getFirst(), ":"))).mapToLong(Long::parseLong).toArray();
    }

    public List<Triple<Long, Long, Long>> getList(String mapKey) {
        return maps.get(mapKey);
    }

    /**
     * Be careful - with large numbers this will cause memory issues
     *
     * @param mapKey the map being queried.
     * @return Map of the source to destimation values
     * @deprecated use getSourceToDestination instead
     */
    public Map<Long, Long> getSourceToDestinationMap(String mapKey) {


        List<Triple<Long, Long, Long>> data = getList(mapKey);
        Map<Long, Long> results = new HashMap<>();
        data.forEach(
                val -> {
                    long dest = val.getLeft();
                    long source = val.getMiddle();
                    long rangeLen = val.getRight();
                    for (int i = 0; i < rangeLen; i++) {
                        results.put(source + i, dest + i);
                        logger.debug("putting in map {} -> {} and {}", mapKey, source + i, dest + i);
                    }
                }
        );
        return results;
    }

    public long getSourceToDestination(long matchOn, String mapKey) {

        AtomicLong destination = new AtomicLong(matchOn);
        List<Triple<Long, Long, Long>> data = getList(mapKey);
        data.forEach(
                val -> {
                    long dest = val.getLeft();
                    long source = val.getMiddle();
                    long rangeLen = val.getRight();
                    logger.debug("source {} <= matchon {} and matchon {} <= {}", source, matchOn, matchOn, (source + rangeLen - 1));
                    if (source <= matchOn && matchOn <= (source + rangeLen - 1)) {
                        logger.debug("match on {} in range {} {} {}", matchOn, dest, source, rangeLen);
                        destination.set(matchOn - source + dest);
                    }
                }
        );
        logger.info("for map {} and match on {} we have {}", mapKey, matchOn, destination.get());
        return destination.get();
    }


    public long lowestNumber(boolean useRange) {
        List<Long> locations = new ArrayList<>();
        LongStream seedsStream = useRange ? seedRange() : Arrays.stream(seeds());
        seedsStream.parallel().forEach(
                seed -> {
                    logger.debug("finding location for seed = {}", seed);
                    locations.add(getLocation(seed, SEED_TO_SOIL, SOIL_TO_FERTILIZER, FERTILIZER_TO_WATER,
                            WATER_TO_LIGHT, LIGHT_TO_TEMP, TEMP_TO_HUMIDITY, HUMIDITY_TO_LOCATION));
                    logger.info("locations size = {}", locations.size());
                }
        );
        locations.forEach(val -> logger.info("location = {}", val));
        return locations.stream().mapToLong(Long::longValue).min().orElse(0);

    }

    private LongStream seedRange() {
        LongStream longStream = LongStream.range(seeds[0], seeds[0] + seeds[1]);
        for (int i = 2; i < seeds().length; i += 2) {
            long start = seeds[i];
            long range = seeds[i + 1];
            longStream = LongStream.concat(longStream, LongStream.range(start, start + range));
        }

        return longStream;
    }

    private Long getLocation(long seed, String... keys) {
        AtomicLong finalLocation = new AtomicLong(seed);
        Arrays.stream(keys).forEach(key -> finalLocation.set(getSourceToDestination(finalLocation.get(), key))
        );
        logger.info("final location = {} for seed {}", finalLocation, seed);
        return finalLocation.get();

    }

}
