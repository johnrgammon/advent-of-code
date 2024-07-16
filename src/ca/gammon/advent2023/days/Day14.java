package ca.gammon.advent2023.days;

import ca.gammon.advent2023.days.pojos.Coordinate;
import ca.gammon.advent2023.days.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day14 {

    private static final int EXPECTED_RESULT_DEMO_PART_ONE = 136;
    private static final int EXPECTED_RESULT_DEMO_PART_TWO = 64;
    private static final int EXPECTED_RESULT_PART_ONE = 108826;
    private static final int EXPECTED_RESULT_PART_TWO = 99291;

    private static final String DEMO_DATA;
    private static final String DATA;
    static {
        //region demo_data
        DEMO_DATA = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....""";
        //endregion demo_data
        //region data
        DATA = """
                .O..........O.....O.#................#..O#.#......O...O.O...#....#...O.#...#O.O....#.#.........O....
                ...##OO.#O#......#.#..OO.O....###..#..O..#.O.OO...O.........O......O.O##.O..O.O..#O.#..O...#.OOO.O.O
                #.O..OO.O..O.#.#..#.O.O..#..O...........O.OOOO...#..O.......O...O#......O.O..##O...O........#.......
                ...O...#..O.OO##.O#..O.#O..#....O....#...O.......O.O....#..##.....O.##.#.OOO.......OO......O....O.O.
                ....O...#..###..O.....O.#O.###..#O..O.....O.#.##OO..OO.O#.OO.#.....O....#.#O##.O.O.O...#O..O.#O.OOOO
                #O.........OO....#.....O#..#.##........O..O#........#........O#O.O.O..#..OO.#O.#......O.#O..........
                ..OOO.O.#.#.O.#...O.#...O..O.O...#O.#O#O.#......OOO.O.#.....OOO.###.....OO#.#OO...##.....#O#....#...
                .O...O#.....O..O...#.#.....O.O......#.....O....#.#OOO..#O........O.O...O..O##.O.....#..#.O..#O.#....
                #.OO..#.O.......O.O.O##.O.O...................##.......#.........OO#.O....##..O.O..O.#.....O#..#.#..
                #OOO..OOO.OO.#....#.O.....##.....O..#.OO#O.#..#..O..#..O.O...O....#..#...O.####..#O#.O....#.O.O.#...
                OO..O.#O..O.O#O..#......O.#O...#....O........O....#..O.#O....#....O..#....O..OO##O#......#O.O.O..#O#
                ..O..##.......O.....##....O#..........#.#.O.##.......#.OO.#.#..O.#...O.....#OO.....O.O.O#........O.O
                #..O....#.#..#O...#...#.....OO#..#.#.O..OO....#.#.O...O...O....O..#.#..O.#O.....O..##.O.O..O....#.##
                O##.......##.OO..O.#.O..O.O#......O..OOO.....O...OO...O...#.O.O#..O..O.##O.#.....O..O.#O.#...O..O.OO
                #O..O....OO.#..#.O...O.O.....O##......O.......O...#.#.....#..O........O.O...O....#O#..O.....#.O....O
                O.......#...O...O.O...O....O.......O..#.#.O.##.......O#..O#....O###.O....#...OO...#.#......#........
                ....O.#.O##...#O..#OO.........O...O#O..O.....#..#...#........#....#..O.##......#.....O.O..OOOO..#...
                ....#....O#..#..O..........O....OO...O....O.....O.O#..OOO..O.O#..#...O#..#OO.O.O.O.O##.#...##....#..
                .#..OO..OO.O#......#OO.O.O.#..O...##.#.....O......#....#..O#OO.#.....#..OO.O#O...#....#...O###O..OO#
                #OO..#.O.O##.O......O..O......O.O.....O..O...#.O..O#..OOO.#O#O.#.#.#...##.#.O.O.......O....#..O..#O.
                #.#...#....#.#..#.O.####.O...#O#.#....#O..........O.OO...#...........#.#.#..OO.#O..#..#O.O.O..#.O...
                .O.##..#...OOO#O#..##.#O....O..#.O.#.#.OO.#..#...O.##...#......O#O#OOO.OO.#...O.O.......#O.O.OOOO##.
                ......O.........#..O...#...O.#O...#O.O#....#.O..O.OO...OO.O..O#.##...#...#......#.#.O...O#..##.OO.O.
                O........O#.O#......O#...#..O#..OO....#.....OOO..O.O...#.O##..#...O..O.....................OOO......
                OO...#O....OOO.O.O....#.O..O##OO..O.....#..O#.....#....O.....O..#...OO..#....OO#.#.O.....OOO#..O.OOO
                .....#.##..O#O....O#...#.......##.#..O..............#..#...O.OO......#.......O#....#..O.#..#.O..#..#
                .#.O.....O##O.#.#.#O.#.....#.......O...O.......#O.O.....O#O#O..#O..#O......OO...O#.O..O....##..O....
                .......#.O.O#O#..##..#.OOOO.OO.##.#OO.#O#..O...O..OO.O.#.O#..O#O#O.O....#O.......OO..O...#...O.#....
                #O.#...OO#.OO......O.O.O....#O....#O#O....O#..#O#.#....O#.##.#..O.......OO..O..O#OO#O....O#....##..#
                ..O.OO.O......O.OO....O#...O#O#.O##.....#OO...O....O.##..##..O.O.#..O##O.#O##.#.OOO.#.###..#OO...#O.
                O....O..OO#O#...##.....OO....#..O...O.##.......O..#..##OO......#..O.....#..O.#...#.#..O..##O.......O
                ##.#..#..#..O.....O.#.##.......#.##........#....#.....O.....O.O#O..O.#O.#..#.#O.....O..O..#.O....OOO
                .O.#O.#...O..#.......#..#O..#.O.OO...O..O...O.O..O...#..##OO..#......###O#.O..O..#.##........#....#O
                ..O....##...O#OOO.#O...#..O.O.#.O....##..#.#.O.OO..O#OO....O..OO.OO#......O.O........O.#O.....O#.O..
                .OO#...#.OO.OO#.#...OO#.OOOO.O..O.#.OOO....#.OO...O.##..OO#OO.......#O.....O..##....O#.#.OO...O...#.
                ..#O.OO.O.#O.#...#.....O.#.#..##..O..##...OO#....#.#....O....##..O.........#...OOO..O..#O.....#...#.
                #.O.....#..O...OO#....OO....##.....#.........O.O#.O.##.#OO..#.....#O..O............#.....#.......OO.
                O..O.#.....O#.O..#O.........##O##..#........#..OO.....#.#...O....O..#.O......#O....OO.........#..OOO
                O....#...O...#..........O..O#OO..#O..OO.#OOO....#..####OO#........#O...##.O...#.......OO.O#.....#..#
                .#............#.#OOO..O##O...OO..O.#.O...O#O..#.##O..OO.#.O..OOO...#O.#.#O.....#..O...###O..#..#....
                .....O.OOOOO..#.#OO...O.O.......#OO...#....OOO#......O...O.....O.#.........OOOO.......O......#.OO...
                ...#..O...O..O.#O.#O....##OO.O...O....O#....O...O....O#.O..#OO#.......#.O.............#.#O..........
                ..#.###...O#..O.#.O..#OO.O.OO...#.......O..#...OOO#O.....OO.###...O.#O.OO.O..O#O....O#.#...#...O..O.
                ..O.#...#...O#.O..#....#.O..O...#O....O.O..#..OO.........O#O.O.#..#O.#..O.O##..O..O......O#...O..#.#
                O.O..O..O##.#O##O.#.O..OOO.......#..OO.O.##.#O.OOO.....O.O.O....O..#OO..O.....#O.....#....O..O..O#.O
                .#...O.O..O....O...#O#.#O.##...#...#..O.OO#.....#O.O#....O#.##.O.O.O..#OO####......O#O....O......O#O
                ##.O..#...O....#....#.##.O.........O.##.O..#...#O..#.....O...#.O#........#.O#..OO...#.#OO...#....#..
                .O...#O.O...O..#O.O#OO.#.....O.....##O..#.#..OO.O#.##.#.###O...OO....O....#.O.#...O...O....O.#..#..#
                .O#....O.#......O...##.O...O.#.O#OO.....O#O.#.O..O.#O..#..#.....O..O.#.O..O..#....O..O#OO...##O.O...
                .O.....#O#.......##O.......#O.O........OOO.......#.O#..#.......O....##....#.#O.......O.....O#....#OO
                ..#.#.OO.#.O.....O#.....#..O...OO.....OO.......O.O##..#.........#........O.O#O.....##...##..........
                #O...............O.O.#.....O#O.....O..#.O....OO#O.O...##.....#.O#....O#..OOO.O.#.OO.#..#O#.O.####.#.
                O#O.#.O#O...O.....#.O........#..O.....#..O.O..#.#.O.O#....O........O.O...O....O#...#OO#..O.###.O#.O.
                ..OO......#......#..O...O#O#..#O.#.#.#......#.....#.#..#...O.....#.O#O.OO...#O...#..#..OO.#...O.O#..
                ..#....O.............O#.#O.....##......O#O.O#.#O##..OO..##O.#O......O#O.O.#O#.#.#.#.#..#....OO.....O
                ...........#..O.O#.......O...#..O.O#..O.#....#...O...O.#.OO.....#....#..#...#..O...O.#......O#O.O.#.
                .O#..O..O#..O.....O.#..#.#...O...O..O.OO.#...O.O......O.......OOO..#.#O..#....O..#O#...#.OO.O...#OO.
                O.OO..O.....#.OOO...........O..#...........O........OO.#...O..#.O..O..O....#..##....O#.OO.OOO..##O##
                ..OOO.#...O#O###.OO...O..#.#.OO..O....O...#..#.#.#...O...##..O..OO##..OO...#....#...#.O.O.O..#......
                ..O..O.##O#.O..##.O..OO..O....#.#.O..#OO...O..O#.#.........O.......O#.OO........##....O..O#...#...#.
                ..#...O#O....OO.#...O#......#.O.O#O...#.O.#O.......O....#..O.#.....#...OO.O.#O..#....O..#..O.O#.#..O
                .O.......#.OO....O.OO..##.#O#.O.O.#OOO.##.O.O...#.O....O#O#O..#..O.O......O#..O...#....#...#.#.O#...
                ....O.....O..O...O.#OOO.OO...O...O.O.#.....O#......OOO.OOO.#..##..#..OO............O.O.....O...O....
                O#O..O.OOO......O...O...OO.O.....O#.O..#...O##.OOO.#...O.#.O#O....#O.#..#....O.O#....O.O...O.O.O...O
                ....#.O#.....O.#.#O.........#...#O...O.O#OO...#.......#O...#...........O..O.....O...O#.#O...#..O..O.
                .#....#.......#..O#O..O.......O#O....O.O..##.O..O....O#..O.O..O....O#..OOO.O..O.#.#....##.##O.O.O.O.
                ...O##.OO........OO#.O..O....O.#.O............#....#O#.O#...#..O#####..##.......#.......#...##.O....
                #..O...#...O....O.O.##O.#O..O#...O...#..#O.#.O.O.O###...O.....O......#....O#...OO.O...#...#.O...OO..
                #.....#.OOO..O....#O##...OO....O.#OO.O..#.#..OO.#.O..##O.###O..##..O...##.....#.OO..O.#.OO.O..#...O.
                .#O#...O...OO.O.....#........O##.#.O#O...O..#OO.#.....O....O.O.O..O.#..O..O..O..#..#...#.#OO...O..#O
                ..#...#......O....OO.O.#..O....O..O...#.......##O#.#OOO..#.##...O..O...O..#......#.O....OO..O.O..O#O
                ..##.O.........O........O#.#..O.O...O.O##O.....O.OO......O#.....#O.....##OO#O#..O#.O......O....#...O
                .O.#...#..........#..##...#.O...#..........#O.#.#....#..O..##..#.O..O.......O......O.#O.#O...#...#..
                ..O.OO.....O.O.O.OO..O.O#O.OO.........OOO.O..O..OOOO...O....O#...#..O.#.O.O..O..OO...O..OO.O.##..OO#
                ..O#..#..O..#..OOOO......##.#..#....#...OO.O.O#.#.....O...#OO....O..##..#.#...##.#...O.##O...O......
                .....O.O..O....#.O#....#..O.......#......#..##...#...#.....#O..O..OOO.....O.........OO.#O..O..O.#O.#
                ##.#OO......##..O#..#.OOO..OO.#....OO.#.OO..#..#.......O#...OO.#.O........O.#.O...#O....#....O...O.O
                OO.O...#..O..O.O#.#O..........#....O....O.#.....#.O......#....O...O#..OO.#...OO....OO.O...#...OO..##
                ..O...O...O##..O...O.....##..OO....OO..O.......O.O.#..#.O.O..O..OO#....O..#..#.O......#O#O.O#.O#OO..
                ...O.OO.....O....#..#....O...#..O.....#OO#O#.#.###...#.O.O.O#.#....#..O...O.....OO..#....OO#...#....
                .O.OO....O#.....OO.O#O..#...#....#..O..O....#....O....O.O.#O.##....O........O#.O....O#.O.O#...#O#...
                ...O.#.#.....OO.OO#.O......#......##...#.#..#O.....#O...O#..#O#O..#..O.....OO##.......O.#.O#....O.#.
                ..#....O.....##.....O#.O...O.#..#......O....#......OO.O.#....#..............#.....O.......#O.....#.O
                ..O#...#.O..#.OO..#..O...OOOOOOO.OO#..O........#....O....OO.#OO.......#..O.O...OO.....O.OOOO#O...O..
                .O#.#O.#O...#...O#..........O.....O.O.#.....#....O..OO.OOOO......#..O#..#..#....O....O.OO.#....#O.O#
                ..#OO....O#....O..#....#....O.#...OO#O....OO#.........O..O.O.....#OO....#O##..OOO.#..#O..O..O...O...
                .O...#O#..O....OO...O.#...O....#....O...#....#O.#O.O.#O#.O.O#......O............#....#...O.O.#......
                #OOO..##....#O.O..OO..O##.......O#.##O..#....#.##.#.#OO........#....#....O...#........O...O..O.O#...
                ...#.#..#...O...OO.O..#O...O.O......#...OO...OO.#O.....O..O##.##..O.....O......O.OO..#.O....O.#OOO.O
                ...O.....#........#O.#.OO.#.......O...O....O.#....#.#......O.O..#.#.O.......##......OO.O..O..#OO.O#.
                ##.O..O#..#...#.#O...O.......#O.OO.....OO...O.OO.....O.......O.....O#....O...O#.O...O..##....#O.O.#.
                O.O.#OO.O.#.....#O.O..#O.O...##...#.O......OO.#...#..O.OO.O..........#..OO.....OO..O#.#...#.O..O#O..
                .O.#O#O....O...##....O.O.##..........#O.#O#.#..O.#..#O....O..OO......O...#...##O.O.O#.####.OO.O#.O#.
                .....OO.#.O..#O###OO#O.O.O.#.......#.#...#.#O....O.O..........#O....O..##.#.....OOO##..O.....#.....#
                #....O..O.O.O...O#OO.O...O......O....O#..#....#.O..#..#.O..##O#..OO..O.#.O#..OO.....O.....OO#O....#O
                .....OOO...#...#..#.#...O#.O..#.O#O.O##...O.....O...O.#.O..OO......O...O.........#O..#O.#O.....O...O
                O........##..O..OO#....#.#O#.O.O..#..OO........OOO..O.OO.#..OO.#..O..##.O...OO.OO....O##.O......O.O.
                #O.#.#.O#..O.O........#...O...O.#...##.#..O.#..#..O..#O..O.......O....O.O#O...........O#...#...O#...
                ...#O..#...#..O###....#O#..O..O...#OO...OO.OO#OO.#...O....O.........#..#O......#..#.......#O#O#..#O.
                ..O#.....#..##.O#.........#O...#.#.....O..#..#.#OO.#.O.....#O.....O.#.......O....#O..O#..O..O.O...OO""";
        //endregion data
    }

    private static class PlatformSolver {
        private final Set<Coordinate> cubicRockCoordinates;
        private final Set<Coordinate> roundRockCoordinates;
        private final int height;
        private final int width;

        public PlatformSolver(String dataString) {
            final char CUBIC_ROCK = '#';
            final char ROUND_ROCK = 'O';
            final char END_OF_ROW = '\n';
            // SPACE char ignored

            cubicRockCoordinates = new HashSet<>();
            roundRockCoordinates = new HashSet<>();

            int row = 0;
            int column = 0;
            for (char ch : dataString.toCharArray()) {

                if (ch == END_OF_ROW) {
                    row++;
                    column = 0;
                    continue;
                }

                if (ch == ROUND_ROCK) {
                    roundRockCoordinates.add(new Coordinate(row, column));
                } else if (ch == CUBIC_ROCK) {
                    cubicRockCoordinates.add(new Coordinate(row, column));
                }

                column++;
            }

            width = dataString.split(END_OF_ROW + "")[0].length();
            height = row + 1; // started at 0, add 1
        }

        public void longitudinalShift(boolean isNorth) {
            int startRow = isNorth ? 0 : height - 1;
            int rowIncrement = isNorth ? 1 : -1;

            for (int col = 0; col < width; col++) {
                int firstAvailableRow = startRow;
                int row = startRow;
                while ((isNorth && row < height) || (!isNorth && row >= 0)) {
                    Coordinate checkCoord = new Coordinate(row, col);

                    if (cubicRockCoordinates.contains(checkCoord)) {
                        firstAvailableRow = row + rowIncrement;
                        row += rowIncrement;
                        continue;
                    }

                    if (!roundRockCoordinates.contains(checkCoord)) {
                        row += rowIncrement;
                        continue;
                    }

                    if ((isNorth && row <= firstAvailableRow) || (!isNorth && row >= firstAvailableRow)) {
                        firstAvailableRow += rowIncrement;
                        row += rowIncrement;
                        continue;
                    }

                    Coordinate newCoord = new Coordinate(firstAvailableRow, col);
                    roundRockCoordinates.remove(checkCoord);
                    roundRockCoordinates.add(newCoord);
                    firstAvailableRow += rowIncrement;
                    row += rowIncrement;
                }
            }
        }

        public void latitudinalShift(boolean isWest) {
            int startCol = isWest ? 0 : width - 1;
            int colIncrement = isWest ? 1 : -1;

            IntStream.range(0, width).forEach(row -> {
                int firstAvailableCol = startCol;
                int col = startCol;
                while ((isWest && col < width) || (!isWest && col >= 0)) {
                    Coordinate checkCoord = new Coordinate(row, col);

                    if (cubicRockCoordinates.contains(checkCoord)) {
                        firstAvailableCol = col + colIncrement;
                        col += colIncrement;
                        continue;
                    }

                    if (!roundRockCoordinates.contains(checkCoord)) {
                        col += colIncrement;
                        continue;
                    }

                    if ((isWest && col <= firstAvailableCol) || (!isWest && col >= firstAvailableCol)) {
                        firstAvailableCol += colIncrement;
                        col += colIncrement;
                        continue;
                    }

                    Coordinate newCoord = new Coordinate(row, firstAvailableCol);
                    roundRockCoordinates.remove(checkCoord);
                    roundRockCoordinates.add(newCoord);
                    firstAvailableCol += colIncrement;
                    col += colIncrement;
                }
            });
        }

        public PlatformSolver shiftRocksNorth() {
            longitudinalShift(true);
            return this;
        }

        public PlatformSolver spinCycle() {
            longitudinalShift(true);
            latitudinalShift(true);
            longitudinalShift(false);
            latitudinalShift(false);
            return this;
        }

        public int getNorthernLoad() {
            int load = 0;

            for (Coordinate coordinate : roundRockCoordinates) {
                load += height - coordinate.getRow();
            }

            return load;
        }

        public String memoize() {
            return roundRockCoordinates.toString();
        }
    }

    public static void main(int part, boolean isDemo) {
        long startTime = System.nanoTime();
        int expectedResult;
        int actualResult;

        String dataString = isDemo ? DEMO_DATA : DATA;

        if (part == 1) {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_ONE : EXPECTED_RESULT_PART_ONE;

            actualResult = new PlatformSolver(dataString)
                    .shiftRocksNorth()
                    .getNorthernLoad();

        }
        else {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_TWO : EXPECTED_RESULT_PART_TWO;
            List<String> memoKeys = new ArrayList<>();
            List<Integer> solutions = new ArrayList<>();

            int repetitionStart = 0;

            PlatformSolver platformSolver = new PlatformSolver(dataString);

            for (int i = 0; i < 1_000_000_000; i++) {

                String memoKey = platformSolver.spinCycle().memoize();

                if (memoKeys.contains(memoKey)) {
                    repetitionStart = memoKeys.indexOf(memoKey) + 1;
                    break;
                } else {
                    memoKeys.add(memoKey);
                    solutions.add(platformSolver.getNorthernLoad());
                }
            }

            int solutionIndex = (1_000_000_000 - repetitionStart) % (memoKeys.size() - repetitionStart + 1) + repetitionStart - 1;
            actualResult = solutions.get(solutionIndex);
        }

        Utils.publishResult(startTime, expectedResult, actualResult);
    }
}
