package com.advent2023.days.pojos;

import java.util.Objects;

public record Coordinate(int row, int column) implements Comparable<Coordinate> {

    public Coordinate move(Direction direction) {
        int rowAdj = 0;
        int colAdj = 0;

        switch (direction) {
            case NORTH -> rowAdj--;
            case SOUTH -> rowAdj++;
            case WEST -> colAdj--;
            case EAST -> colAdj++;
        }

        return move(rowAdj, colAdj);
    }

    private Coordinate move(int rowAdjustment, int columnAdjustment) {
        int row = this.row + rowAdjustment;
        int column = this.column + columnAdjustment;

        return new Coordinate(row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int compareTo(Coordinate other) {
        if (this.row != other.row) {
            return Integer.compare(this.row, other.row);
        }
        return Integer.compare(this.column, other.column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Coordinate[row " + row + ",column=" + column + ']';
    }
}
