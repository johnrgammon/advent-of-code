package com.advent2023;


import com.advent2023.days.*;

import java.util.List;

public class Main {

    private static final boolean IS_DEMO = true;
    private static final List<Integer> PARTS = List.of(1);

    public static void main(String[] args) {

        PARTS.forEach(part -> {
            String message = "Advent 2023 " + (IS_DEMO ? "Demo " : "") + "- Part " + part;
            System.out.println("\n\033[1m\033[4m" + message + "\033[0m");

            Day18.main(part, IS_DEMO);
        });
    }
}
