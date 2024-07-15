package com.advent2023.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.advent2023.days.utils.Utils;

public class Day6 {

    private final static long EXPECTED_RESULT_PART_ONE_DEMO = 288;
    private final static long EXPECTED_RESULT_PART_ONE = 1084752;
    private final static long EXPECTED_RESULT_PART_TWO_DEMO = 71503;
    private final static long EXPECTED_RESULT_PART_TWO = 28228952;

    private final static String DATA_DEMO;
    private final static String DATA;
    static {
        DATA_DEMO = "Time:      7  15   30\n" +
                "Distance:  9  40  200";
        DATA = "Time:        40     70     98     79\n" +
                "Distance:   215   1051   2147   1005";
    }

    private static long firstToBeatRecord(long raceTime, long recordDistance) {
        for (long buttonPressTime = 1; buttonPressTime < raceTime; buttonPressTime++) {
            long distance = buttonPressTime * (raceTime - buttonPressTime);
            if (distance > recordDistance) {
                return buttonPressTime;
            }
        }
        return 0L;
    }

    private static long lastToBeatRecord(long raceTime, long recordDistance) {
        for (long buttonPressTime = raceTime - 1; buttonPressTime > 0; buttonPressTime--) {
            long distance = buttonPressTime * (raceTime - buttonPressTime);
            if (distance > recordDistance) {
                return buttonPressTime;
            }
        }
        return 0L;
    }

    public static void main(int part, boolean isDemo) {
        String dataStr = isDemo ? DATA_DEMO : DATA;
        List<Long> winningButtonPresses = new ArrayList<>();
        long expectedResult = 0L;
        long startTime = System.nanoTime();

        if (part == 1) {
            String[] raceTimes = dataStr.split("\n")[0].replace("Time:", "").trim().replaceAll(" +", " ").split(" ");
            String[] recordDistances = dataStr.split("\n")[1].replace("Distance:", "").trim().replaceAll(" +", " ").split(" ");

            TreeMap<Long, Long> raceTimeToRecord = new TreeMap<>();
            for (int i = 0; i < raceTimes.length; i++) {
                String raceTimeStr = raceTimes[i];
                String recordDistanceStr = recordDistances[i];

                raceTimeToRecord.put(Long.parseLong(raceTimeStr), Long.parseLong(recordDistanceStr));
            }

            for (Map.Entry<Long, Long> entry : raceTimeToRecord.entrySet()) {
                long raceTime = entry.getKey();
                long recordDistance = entry.getValue();

                long firstToBeatRecord = firstToBeatRecord(raceTime, recordDistance);
                long lastToBeatRecord = lastToBeatRecord(raceTime, recordDistance);

                winningButtonPresses.add(lastToBeatRecord - firstToBeatRecord + 1);
                expectedResult = isDemo ? EXPECTED_RESULT_PART_ONE_DEMO : EXPECTED_RESULT_PART_ONE;
            }
        }
        else {
            long raceTime = Long.parseLong(dataStr.split("\n")[0].replace("Time:", "").trim().replaceAll(" +", ""));
            long recordDistance = Long.parseLong(dataStr.split("\n")[1].replace("Distance:", "").trim().replaceAll(" +", ""));

            long firstToBeatRecord = firstToBeatRecord(raceTime, recordDistance);
            long lastToBeatRecord = lastToBeatRecord(raceTime, recordDistance);

            winningButtonPresses.add(lastToBeatRecord - firstToBeatRecord + 1);
            expectedResult = isDemo ? EXPECTED_RESULT_PART_TWO_DEMO : EXPECTED_RESULT_PART_TWO;
        }

        long actualResult = winningButtonPresses.stream().reduce(1L, (a, b) -> a * b);

        Utils.publishResult(startTime, expectedResult, actualResult);
    }
}
