package ca.gammon.advent2023.days;

import ca.gammon.advent2023.days.pojos.Coordinate;
import ca.gammon.advent2023.days.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day11 {

    private static final int PART_ONE_DEMO_EXPECTED_RESULT = 374;
    private static final int PART_TWO_DEMO_EXPECTED_RESULT = 8410;
    private static final long PART_ONE_EXPECTED_RESULT = 10173804;
    private static final long PART_TWO_EXPECTED_RESULT = 634_324_905_172L;
    private static final String DEMO_DATA;
    private static final String DATA;
    static {
        //region demo
        DEMO_DATA = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....""";
        //endregion
        //region data
        DATA = """
                .......................#.........#...............#....................................#..............#......#...............#...............
                .#........#.................................................................#.........................................................#.....
                .............................#..............................................................#..............................................#
                ...............#.....................................#..........................................................#......#....................
                .......#....................................#.............#..............#......#..............................................#............
                .......................................#.................................................#..................................................
                ..........................#......#...........................................................................#..............................
                ...................#........................................................#............................................................#..
                .......................................................#.........#..........................#......................#................#.......
                ...........#........................#.................................#.....................................................................
                ...............................#..............#......................................#............#..........................#..............
                ..#..................#..................................................................................#.......#...........................
                .........................................#..................................................................................................
                ...........................#...............................................................#.........................#......................
                ...................................................#............#..........................................#...............#............#...
                ...........................................................#.............#..................................................................
                ...........#.........................................................................#......................................................
                ......................................................................................................#...........................#.........
                ...#..............#..........#...................#......#..........#.............................#...............#........................#.
                .....................................#........................................#.............................................................
                ........................................................................................#...................................................
                ..........#..................................................................................................#.............#..........#.....
                ........................#............................#......................................................................................
                ....#....................................#........................#.................................#.......................................
                .................................................#..........................#.............................#.................................
                ............................#...................................................................#..............................#...........#
                .................#..........................................#..........#.................#..................................................
                ......................................................................................................#............#........................
                ........................#..........#............................#..........................................................#................
                ................................................................................#..............................#............................
                .......#......#..........................................................................................................................#..
                ............................................................................#...................................................#...........
                ...................#.............#.....#...............#................................................#.........#.........................
                ..#.......................................................................................#.................................................
                ............#..............................#.........................#............#.............#...........................................
                ....................................................#.........................................................#............#................
                .....................#.......................................#.......................................................................#......
                #.........................................................................#............#....................................................
                ......#..............................#........................................................................................#.............
                ........................#.....................#.............................................................................................
                ..........................................................#...........#......#.......................................#......................
                ...#.........................#....................................................................#.........................................
                ..................................#...........................#....................#......#...............#.................................
                ..................................................................................................................#...................#.....
                ......................................................#.......................................#.........................#...................
                .........#...........#............................................#.........................................................................
                ............................................................................................................................................
                ..............#.............#....................................................#.......................#......................#.......#...
                ...................................#........................................................................................................
                ............................................................................................................................................
                ...#.............................................#..............................................#...........................................
                ........#................#.....#..........................#.................#..............#......................#.........................
                ..................#....................................................................................................#....................
                ...............................................................#.....#......................................................................
                ....................................................................................................#........#.............................#
                ..........#...................................................................#.....#...............................................#.......
                ...#....................#............................#.....#.........................................................#......................
                ............................................................................................................................................
                .............................................#....................................................#......................................#..
                ...................................................................#......................................#.....................#...........
                ................#...............#.......................................................#...................................................
                .........................................................#...................................#....................#.........................
                ...#.............................................#...................................................................................#......
                ...................#..........................................#................#.............................#..............................
                .....................................................................#.....................................................#................
                .........#.....................#.......................................................#.........#..................#.......................
                .........................................................................................................#........................#.........
                ...........................#...............................................................#............................#...................
                ...............#.................................................#..........................................................................
                ..#...................#..............................#.....#...............#..........................#.........#...........................
                ............................................................................................................................................
                ..........#.................................#...............................................................................................
                ..................#....................#..............................................................................#..............#......
                .........................................................#..................................#..................................#............
                ..............................#...............................................#..............................#..............................
                #.............#...................................#.........................................................................................
                .................................................................#.....................#...............#....................................
                ......#...................#...........................................#..................................................#..................
                ...........#.......#...................#......................................................#.............................................
                ..#.................................................#.........#.....................#....................................................#..
                .................................#.................................................................#..................#.....................
                ..........................................................................#...............#...................................#.............
                ............................................................................................................................................
                #.........#..........................#.................#.................................................#..................................
                .....#..........#............#....................................................#...........................#..................#.....#....
                ............................................................................................................................................
                .........................#.......#.........................#.........................................................#......................
                ............................................................................................................................................
                .......#...............................................................#.................#..................#...............................
                ............................................................................................................................................
                ...........................................#.........................................................#......................................
                .........................................................#...................#................#................#....................#.......
                .#...........................#.......................................................#...................................................#..
                .....................................................................#..................................................#...................
                ...........#.......#................................#..........#............................................................................
                ........................#.......#.........................................#.....#.......................#...................................
                .....#.....................................#........................................................................#.........#.............
                ...................................................................................................................................#........
                ...............#..................................#........................................#.......#........................................
                .....................................#..................................#...................................................................
                .....................#.....#.........................................................#.........................#............................
                ...........#..........................................................................................................#.....................
                .......................................................#....................................................................................
                .............................................................#.....#............................#.......................................#...
                ...#.......................................................................#.......#....................#...................................
                ...........................................#.................................................................#.....................#........
                .........................#..................................................................................................................
                ............#......#..............#.................#......................................................................#................
                .......#......................................#....................................................#...............#........................
                ......................................#.................#..........#......#.................................................................
                #........................................................................................#..................................................
                ............................#.................................#....................................................................#.......#
                .......................................................................................................#....................................
                .........#.....#.....................................#..........................................#...........................................
                ....................................................................................#...........................#...........................
                .......................#.........#.........................................................#..................................#.............
                ...........................................#......#........#.............................................#............#.....................
                ......................................................................#............................#........................................
                ......#......................#...................................#.............#..............................#.....................#.......
                ...............#...................................................................................................#........................
                .......................................................................................................#....................................
                ....................................................................................#......#................................................
                .........................................................#........................................#........#....................#.......#...
                .........................#.........#...............#.....................#.................................................#................
                ...................#..............................................#.........................................................................
                ........................................#......................................................#............................................
                .....#..............................................................................................................#.........#.............
                .....................................................................#......................................................................
                ............#...........................................#...........................#.......................................................
                .#.......................#............#..........................#.......................#...............................................#..
                ..............................................#.....#.....................................................#......#..............#...........
                ...............#............................................................................................................................
                .............................................................#........#.....#..............................................#................
                ..........#......................................#...................................................................................#......
                ..#.....................#......#.........#............#.............................................#...............#.......................
                ............................................................................................................................................
                .......#..................................................................................................#.................................
                ............................#.............................#..............................#................................................#.
                ............#......................................................#.......#.............................................#.....#............
                ..................#................................................................................................#........................""";
        //endregion
    }

    private static final int EXPANSION_FACTOR_PART_ONE = 1;
    private static final int EXPANSION_FACTOR_PART_TWO = 999_999;
    private static final int EXPANSION_FACTOR_PART_TWO_DEMO = 99;

    private static class GalaxyPath {
        private final Coordinate galaxy1;
        private final Coordinate galaxy2;
        private final long rawPathLength;

        public GalaxyPath(Coordinate galaxy1, Coordinate galaxy2) {
            this.galaxy1 = galaxy1;
            this.galaxy2 = galaxy2;

            this.rawPathLength = Math.abs(galaxy1.getRow() - galaxy2.getRow()) +
                    Math.abs(galaxy1.getColumn() - galaxy2.getColumn());
        }

        public Coordinate getGalaxy1() {
            return galaxy1;
        }

        public Coordinate getGalaxy2() {
            return galaxy2;
        }

        public long getRawPathLength() {
            return rawPathLength;
        }

        @Override
        public String toString() {
            String galaxy1Str = galaxy1.toString();
            String galaxy2Str = galaxy2.toString();
            return galaxy1Str + " - " + galaxy2Str + ": " + rawPathLength;
        }
    }

    private static String dataStr;
    private static Map<Integer, Coordinate> galaxyToCoord;
    private static List<Integer> missingRows;
    private static List<Integer> missingCols;
    private static int expansionFactor;

    private static void setup() {
        int row = 0;
        int column = 0;
        int galIndex = 1;
        galaxyToCoord = new HashMap<>();

        String[] lines = dataStr.split("\n");
        for (String line : lines) {
            char[] chars = line.toCharArray();
            for (char ch : chars) {
                if (ch == '#') {
                    galaxyToCoord.put(galIndex++, new Coordinate(row, column));
                }
                column++;
            }
            row++;
            column = 0;
        }

        List<Integer> rowNumbers = galaxyToCoord.values().stream()
                .map(Coordinate::getRow)
                .distinct()
                .sorted()
                .toList();
        missingRows = IntStream.range(rowNumbers.get(0), rowNumbers.get(rowNumbers.size() - 1))
                .boxed()
                .filter(x -> !rowNumbers.contains(x))
                .toList();

        List<Integer> colNumbers = galaxyToCoord.values().stream()
                .map(Coordinate::getColumn)
                .distinct()
                .sorted()
                .toList();
        missingCols = IntStream.range(colNumbers.get(0), colNumbers.get(colNumbers.size() - 1))
                .boxed()
                .filter(y -> !colNumbers.contains(y))
                .toList();
    }

    private static List<GalaxyPath> getPaths() {
        List<Coordinate> galaxies = new ArrayList<>(galaxyToCoord.values());
        List<GalaxyPath> paths = new ArrayList<>();

        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                paths.add(new GalaxyPath(galaxies.get(i), galaxies.get(j)));
            }
        }

        return paths;
    }

    private static long getPathLength(GalaxyPath galaxyPath) {
        Coordinate galaxy1 = galaxyPath.getGalaxy1();
        Coordinate galaxy2 = galaxyPath.getGalaxy2();

        long rowAdjust = missingRows.stream()
                .filter(rowNumber -> rowNumber < Math.max(galaxy1.getRow(), galaxy2.getRow()))
                .filter(rowNumber -> rowNumber > Math.min(galaxy1.getRow(), galaxy2.getRow()))
                .count() * expansionFactor;

        long colAdjust = missingCols.stream()
                .filter(colNumber -> colNumber < Math.max(galaxy1.getColumn(), galaxy2.getColumn()))
                .filter(colNumber -> colNumber > Math.min(galaxy1.getColumn(), galaxy2.getColumn()))
                .count() * expansionFactor;

        return galaxyPath.getRawPathLength() + rowAdjust + colAdjust;
    }


    public static void main(Object... args) {
        int part = Integer.parseInt(String.valueOf(args[0]));
        boolean isDemo = args.length > 1 && args[1].equals(true);
        long startTime = System.nanoTime();

        long expectedResult;
        long actualResult;
        dataStr = isDemo ? DEMO_DATA : DATA;

        if (part == 1) {
            expectedResult = isDemo ? PART_ONE_DEMO_EXPECTED_RESULT : PART_ONE_EXPECTED_RESULT;
            expansionFactor = EXPANSION_FACTOR_PART_ONE;

        } else {
            expectedResult = isDemo ? PART_TWO_DEMO_EXPECTED_RESULT : PART_TWO_EXPECTED_RESULT;
            expansionFactor = isDemo ? EXPANSION_FACTOR_PART_TWO_DEMO : EXPANSION_FACTOR_PART_TWO;

        }

        setup();

        actualResult = getPaths().stream()
                .mapToLong(Day11::getPathLength)
                .sum();

        Utils.publishResult(startTime, expectedResult, actualResult);
    }
}
