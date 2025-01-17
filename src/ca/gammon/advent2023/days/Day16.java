package ca.gammon.advent2023.days;

import ca.gammon.advent2023.days.pojos.Coordinate;
import ca.gammon.advent2023.days.pojos.Direction;
import ca.gammon.advent2023.days.pojos.Grid;
import ca.gammon.advent2023.days.utils.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static ca.gammon.advent2023.days.pojos.Direction.*;

public class Day16 {

    private static final long EXPECTED_RESULT_DEMO_PART_ONE = 46;
    private static final long EXPECTED_RESULT_DEMO_PART_TWO = 51;
    private static final long EXPECTED_RESULT_PART_ONE = 7498;
    private static final long EXPECTED_RESULT_PART_TWO = 7846;

    private static final String DEMO_DATA;
    private static final String DATA;
    static {
        //region DEMO DATA
        DEMO_DATA = """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....
                """;
        //endregion DEMO
        //region DATA
        DATA = """
                \\|.......|........|...../........................\\.\\.......\\...-../..........\\.........|...\\......|...........
                .|.../............|.....|\\..../....-.\\...........-............./......................|.-.-...........\\.....-.
                ...............|........-....-...../..../|./.............\\..|..................\\..........|\\.........../.\\....
                ....................|\\..................\\..................\\/....................-....../................\\.|..
                ......././-......................................|.....|./.............................../....................
                .......................\\.....|............-...\\..|......-./...\\.........../.............|..-......-...../.....
                .....................-.......\\.......|............./........\\.......|........../............/.................
                ..................-................-./....................|....|...................-..................\\.......
                |.......././.................................|.....-............................-.........|......../../.......
                .........../.......-........|................/......-....|.......\\/..|..\\...|..............-....\\....|...-....
                ............./-......./.....-./....\\........./..|...............|..-......................-...................
                ......-\\.\\..|/......-.|.........-.......-..\\|.............................\\.-.............\\...............\\...
                ......\\..../................\\.........\\..|...............-.......|..-|.....|-..........................-......
                .....\\....../../..........\\......-...........\\.......-.\\........./...........|\\............-.............\\....
                ........\\....................|........./...................|.........-.........\\..............................
                .........-|.............\\-.............................-..............|.....\\../......|/......................
                ..................../.......\\/......./................../...../.......................|......|./.-......-|....
                ..................../.........|..-.-./....../.|-..\\..|........|......-.................\\...\\........./...\\....
                ....-...-....../...............................................|....................\\..-..............-..|.-.\\
                ..........\\..\\.\\....................-...|......................\\......|......-.................\\....|....../..
                \\................../.....-./.......-...........\\..../....../....|.........|........................|..........
                ../......\\.|..............................|../......................-.\\..........\\...............|..........|.
                .|..............................-...-........|...-........|........../............\\...../.\\......-....|.......
                ............./...................\\.\\.........|...|../..........-.......\\........../.-.-.....-..........-......
                ..........................-.|\\......|......./|.............../..../......--.....|.........|........../........
                /-....|/...-|..........\\......................./..........-.....-.............\\....../................\\.......
                ............-../............\\...............................-.........\\...\\...........|-....|.................
                ..............................|........-.........-..................|...........................-.............
                ........./...............//.........................................-..........|............|..../............
                ...\\..||../........................./................|......./.......|....|.....|............|................
                ........./...................\\-./.......\\...../............../..........-|...................|..../.......|...
                ...../................/..-............../....................|/......................................-.|......
                \\|........................................\\...........-......../...\\.|......|..\\../........|.......\\..........
                ..|/......\\....-...............||......//..................\\\\..-./...-....................|...................
                ./....../................\\../.\\.....\\.........\\.\\\\\\..........................-..\\..-............./-...........
                ........|..........-.....|..\\..../....../.................../.............\\...-.\\...........\\...........-.....
                ..........\\...|/..|......|\\/./...........-.|.................-.|....|....................../..................
                -.......................-.|.............-../......./.../...-|..|.........../../..\\............\\../..........|.
                ................./.........\\..........-.........../..|.......//..............|..................|.....\\.......
                .............-../......\\./.../.|.|.....-..|.....|...\\.........|.....-/..................-................|....
                |.-\\..../...........-.....|....................//........\\............./....../........./../\\..\\....-.........
                ......\\.|...........|..............|............/....-../.....\\..................../...................|......
                ../|....\\....................................-.....--...|....../........-...................|...\\.......\\.....
                ..........|../..-.........-...........|....................../............/.......................|.|.........
                ........./..................\\............................\\..............|..................................\\..
                ...-|...........-......\\|\\...........\\...\\.........|..../......../..../..../..-|||................-...........
                ..|....-\\..\\......./...........\\\\.................................|......-./.............../........|..--.....
                .................................../|...........-.......-...|..-............-........../......................
                .......|.............|.......................................................\\...............\\................
                ........\\.......|...........|............................\\-......................................|............
                ......|..\\.-../......................|.-.../.....-..|.-.................................-.......\\......./.|...
                --\\...../..........-...........\\...................../.....|/............\\..............-............./.......
                ......|-...............\\........................................../.-\\..|.....-........|..\\./../.-..........|.
                .........................//.....-...................|...|..-.\\....|................|......\\..-............/...
                ./.........-...-....................../......\\\\......||..........|....................--.................../..
                ....-........\\........................../..........|........./.........................\\.\\....|.......-|......
                .|\\/...............|...-.|....|../..|...|...../......\\..........|.............|........\\.....|................
                ...../.-\\.-................|......../....-..\\......|/.......\\.......................\\.......................|.
                ......................|..........-..../..../.\\../................../......................|..............\\|...
                ........../-\\......|............................../....-......................................./..............
                ./..........|.....-.............-..-...|../........\\.-...........|.........................-................-.
                .-....-................|..................-...-...........-........|.|..........-.-...........................
                ...|...............|.................\\.......|.............../................\\....|.............|.........\\..
                ..\\..............|.............\\..................\\.......................|..........-........................
                ../........./............\\.\\../.........................\\../.....-....|....................-............../...
                ...\\................\\......../......................./\\....\\..-........\\........./..\\.........................
                .........../..|.......-.........-|..............................................\\...............-./\\||...../..
                ...............\\........-..|..........||......................../..........-...../........................\\...
                ...........\\.....................-................................................................|..........|
                ./.........-.......-................................\\..........||..|.|..\\.........\\.../..||......-.......\\....
                \\......\\.........-..\\......\\...................-..|.....\\.....\\....................|..........-......-........
                .............|..........\\..........-..................\\.\\..........|.....................|.............-/.....
                ........-................|......./..-...............\\......|-...\\.....\\.............|./..\\\\../....-.../.......
                ..................\\..||.............-........|............/.........\\../...\\..|/.....\\.........\\..........|...
                ..|../..................\\..............................|...........................................-..........
                \\..................../.|.......\\...|-............../......\\.............././..-...................|...........
                ......./..............................\\..................|................\\............./....-.....-..........
                ..............|.........../...............................\\.................\\..|........-.......-.\\.....|.....
                .................|.....\\........................................-.\\.\\..\\...................//.....\\./.........
                ....../..........|..-............/..........\\............../.............|....................................
                .............|..........-.|..\\..........-....\\...........................|.....................\\.\\....-.......
                ..........\\............../........-........|.....-./...........................................\\..........\\...
                ..........|.-...|.|...............-...|.|..................-...-........|/.../.............................\\..
                .............-....\\.....................................................\\........................|...........\\
                ..\\|...........|....-.-|........................................|.............................\\.....\\.-|......
                ...........//.|.............-......\\.........................|............-|...............././.....|....|....
                .............../.\\...............--........|........-....\\..../.........-.....................-......../...-..
                .....|......\\.........../............/..................../.........|...........-.......-.|...........-.......
                .................-........./..........|...................../..........................|.|...........-.......|
                .....|...-........|...................../.../...................................\\.............\\...|...........
                .............\\.......|........................./....|........./........\\.........\\.......................\\....
                ............................/-..................................../........-..............\\...|.....\\..|\\.....
                ...-..././....................-.......|................./..........-.....|.|..............\\..........-....\\...
                .....\\\\......\\...............................-...-........-.|..../........\\.\\.....-.....................|.....
                ..............\\.........\\.\\.|..........-......//./.../....|.............-.../..|.........................\\....
                ...................../...........\\.........................\\.-..|..........|........./|...............|/...\\|.
                ..|.-./.............-.........././.|......../.......\\...........-.........../..........\\.....-.........-......
                .....|......\\....-.........-.............|.......|-........|-...-../-..|.......-................-.............
                .................|.........|....\\................................/.-.....|........./..../..........-..........
                -....\\......./......\\.\\..................-..|./......-...\\.\\...-.........................\\.............|......
                ......................./..............\\/..........-................../............\\.......\\..\\.......\\....../.
                .|...............//.................................../.......\\.......\\.\\..........-\\...|............-........
                .../......................|.....-.........../................-.../............../.........\\.\\.../........../..
                ..................................|\\..................-../........-./..\\......./....|../.......|-.............
                .....\\..........|................/........\\..........-......../\\../..../.\\-.|...\\....|\\.............\\|..--....
                ................./................................././|..../..........|............../............../.........
                ..............|......|.......\\....|...|......|..................................\\.......-....\\/...........|...
                ......./\\..................................-......./......\\....\\..-..................-................\\......|
                ...........-.........../..............\\............|....../....../.......\\........./......../..\\.........\\...-
                .-..................\\............................/\\...................|...........|....\\....\\--/............/.
                """;
        //endregion DATA
    }

    public enum MirrorType {
        /** The getNextHeading @Override avoids storing a bunch of maps up here. */
        NORTH_SOUTH_SPLITTER('|'),
        WEST_EAST_SPLITTER('-'),
        BACK_DEFLECTOR('\\'),
        DEFLECTOR('/'),
        EMPTY_SPACE('.');

        private final char value;

        MirrorType(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

//        @Override required with Interface in Mirror
        public List<Direction> getNextHeading(Direction currentHeading) {
            return switch (this) {
                case NORTH_SOUTH_SPLITTER -> {
                    if (currentHeading == NORTH || currentHeading == SOUTH) {
                        yield List.of(currentHeading);
                    } else {
                        yield List.of(NORTH, SOUTH);
                    }
                }
                case WEST_EAST_SPLITTER -> {
                    if (currentHeading == WEST || currentHeading == EAST) {
                        yield List.of(currentHeading);
                    } else {
                        yield List.of(WEST, EAST);
                    }
                }
                case BACK_DEFLECTOR ->
                    switch (currentHeading) {
                        case NORTH -> List.of(WEST);
                        case WEST -> List.of(NORTH);
                        case EAST -> List.of(SOUTH);
                        case SOUTH -> List.of(EAST);
                    };
                case DEFLECTOR ->
                    switch (currentHeading) {
                        case NORTH -> List.of(EAST);
                        case WEST -> List.of(SOUTH);
                        case EAST -> List.of(NORTH);
                        case SOUTH -> List.of(WEST);
                    };
                case EMPTY_SPACE -> List.of(currentHeading);
            };
        }

        public static MirrorType fromChar(char symbol) {
            return Arrays.stream(values())
                    .parallel()
                    .filter(mirrorType -> mirrorType.getValue() == symbol)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No MirrorType found for symbol: " + symbol));
        }

        @Override
        public String toString() {
            return name() + " (\"" + value + "\")";
        }
    }

    private static class Mirror {
        private final MirrorType mirrorType;
        private final Coordinate coordinate;
        private Set<Direction> energizedFrom;
        private Direction initialHeading;

        /**
         * Encapsulation: The interface defines a clear contract for mirror behaviour,
         * hiding the implementation details from the Mirror class.
         * This makes the code more modular and easier to understand. ???
         * public interface MirrorInterface {
         *             char getValue();
         *             List<Direction> getNextHeading(Direction currentHeading);
         *         }
         */

        public Mirror(char symbol, int row, int column) {
            this.mirrorType = MirrorType.fromChar(symbol);
            this.coordinate = new Coordinate(row, column);
        }

        public MirrorType getMirrorType() {
            return mirrorType;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public boolean getIsEnergized() {
            return energizedFrom != null && !energizedFrom.isEmpty();
        }

        public Direction getInitialHeading() {
            return initialHeading;
        }

        public void setInitialHeading(Direction initialHeading) {
            this.initialHeading = initialHeading;
        }

        public void addEnergizedFromDirection(Direction direction) {
            if (this.energizedFrom == null) {
                this.energizedFrom = new HashSet<>();
            }
            energizedFrom.add(direction);
        }

        public boolean isNotTrackedFrom(Direction direction) {
            return energizedFrom == null || !energizedFrom.contains(direction);
        }

        @Override
        public String toString() {
            String mirrorString =  "Mirror '" + mirrorType.getValue() +
                    "', (" + coordinate.row() + "," + coordinate.column() + ")";
            if (energizedFrom != null && !energizedFrom.isEmpty()) {
                mirrorString += " - energized!";
            }
            return mirrorString;
        }
    }

    private static class MirrorGrid extends Grid<Mirror> {

        public Mirror getNextMirror(int row, int column, Direction direction) {
            Coordinate newCoordinate = this.getAdjacentCoordinate(new Coordinate(row, column), direction);
            return newCoordinate == null ? null : this.get(newCoordinate);
        }

        public void setInitialHeadings() {
            this.values().forEach(mirror -> {
                int row = mirror.getCoordinate().getRow();
                int column = mirror.getCoordinate().getColumn();
                if (column == 0) {
                    mirror.setInitialHeading(EAST);
                } else if (row == 0) {
                    mirror.setInitialHeading(SOUTH);
                } else if (column == width - 1) {
                    mirror.setInitialHeading(WEST);
                } else if (row == height - 1) {
                    mirror.setInitialHeading(NORTH);
                }
            });
        }

        public void reset() {
            this.values().forEach(mirror -> mirror.energizedFrom = null);
        }
    }

    private static MirrorGrid mirrors;

    private static void loadMirrorGrid(boolean isDemo) {
        mirrors = new MirrorGrid();
        String dataStr = isDemo ? DEMO_DATA : DATA;

        char[] data = dataStr.toCharArray();
        int row = 0;
        int col = 0;

        for (char symbol : data) {
            if (symbol == '\n') {
                col = 0;
                row++;
            } else {
                Mirror mirror = new Mirror(symbol, row, col);
                mirrors.put(row, col, mirror);
                col++;
            }
        }

        mirrors.setInitialHeadings();
    }

    private static void energizeCoordinate(Coordinate coordinate, Direction heading) {
        energizeCoordinate(coordinate.getRow(), coordinate.getColumn(), heading);
    }

    private static void energizeCoordinate(int row, int column, Direction heading) {
        Mirror mirror = mirrors.get(row, column);
        mirror.addEnergizedFromDirection(heading);

        for (Direction direction : mirror.getMirrorType().getNextHeading(heading)) {
            Mirror nextMirror = mirrors.getNextMirror(row, column, direction);
            if (nextMirror != null && nextMirror.isNotTrackedFrom(direction)) {
                energizeCoordinate(nextMirror.getCoordinate(), direction);
            }
        }
    }

    public static void main(int part, boolean isDemo) {
        long startTime = System.nanoTime();
        long expectedResult;
        loadMirrorGrid(isDemo);
        AtomicLong actualResult = new AtomicLong(0);

        if (part == 1) {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_ONE : EXPECTED_RESULT_PART_ONE;

            energizeCoordinate(0, 0, EAST);
            actualResult.set(mirrors.values().stream().filter(Mirror::getIsEnergized).count());

        } else {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_TWO : EXPECTED_RESULT_PART_TWO;
            actualResult.set(0);

            mirrors.entrySet().stream()
                    .filter(entry -> entry.getValue().getInitialHeading() != null)
                    .forEach(entry -> {
                        mirrors.reset();
                        energizeCoordinate(entry.getKey(), entry.getValue().getInitialHeading());
                        long result = mirrors.values().stream().filter(Mirror::getIsEnergized).count();
                        actualResult.updateAndGet(currentMax -> Math.max(currentMax, result));
                    });
        }

        Utils.publishResult(startTime, expectedResult, actualResult.get());

    }
}
