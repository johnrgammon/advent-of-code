package ca.gammon.advent2023.days.pojos;

import java.util.TreeMap;

public abstract class Grid<T> extends TreeMap<Coordinate, T> {
    public long height;
    public long width;

    public void put(int row, int column, T element) {
        super.put(new Coordinate(row, column), element);
        updateDimensions(row, column);
    }

    public T get(int row, int column) {
        return super.get(new Coordinate(row, column));
    }

    protected Coordinate getAdjacentCoordinate(Coordinate coordinate, Direction direction) {
        int newRow = coordinate.getRow();
        int newColumn = coordinate.getColumn();

        switch (direction) {
            case NORTH -> newRow--;
            case SOUTH -> newRow++;
            case WEST -> newColumn--;
            case EAST -> newColumn++;
        }

        return isValidCoordinate(newRow, newColumn) ? new Coordinate(newRow, newColumn) : null;
    }

    public boolean isValidCoordinate(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    protected void updateDimensions(long row, long column) {
        if (row == height) {
            height++;
        }
        if (column == width) {
            width++;
        }
    }
}
