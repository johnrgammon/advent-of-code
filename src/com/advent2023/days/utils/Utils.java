package com.advent2023.days.utils;

public class Utils {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void publishResult(long startTime, long expectedResult, long actualResult) {
        String advent = Thread.currentThread().getStackTrace()[2].getFileName().replace(".java","");
        boolean pass = actualResult == expectedResult;
        String message = pass ? "" : "fail - expected " + expectedResult + ", ";
        String colour = pass ? ANSI_GREEN : ANSI_RED;
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println(colour + advent + ": " + actualResult + " (" + message + duration + "ms)." + ANSI_RESET);
    }
}
