package ca.gammon.advent2023;


import ca.gammon.advent2023.days.Day18;

import java.util.List;

public class Main {

    private static final List<Boolean> IS_DEMO = List.of(true,false);
    private static final List<Integer> PARTS = List.of(1,2);

    public static void main(String[] args) {

        IS_DEMO.forEach(isDemo -> PARTS.forEach(part -> {

            String message = "Advent 2023 " + (isDemo ? "Demo " : "") + "- Part " + part;
            System.out.println("\n\033[1m\033[4m" + message + "\033[0m");

            Day18.main(part, isDemo);

        }));
    }
}
