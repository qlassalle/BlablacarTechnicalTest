package com.qlassalle.core.models;

import com.qlassalle.core.exceptions.InvalidCellException;
import lombok.Getter;

import java.util.List;

public class Lawn {

    private final int width;
    private final int height;
    private final Cell[][] grid;
    @Getter
    private final List<Mower> mowers;

    public Lawn(int width, int height, List<Mower> mowers) {
        this.width = width;
        this.height = height;
        this.grid = initializeGrid(this.width + 1, this.height + 1);
        this.mowers = mowers;
        mowers.forEach(this::placeMower);
    }

    private Cell[][] initializeGrid(int width, int height) {
        Cell[][] cells = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        return cells;
    }

    public Cell getCell(int x, int y) {
        if (x > width || y > height || x < 0 || y < 0) {
            throw new InvalidCellException("The cell " + x + ", " + y + " is invalid");
        }
        return grid[x][y];
    }

    public Cell getCell(Coordinates coordinates) {
        return getCell(coordinates.getX(), coordinates.getY());
    }

    public void placeMower(Mower mower) {
        if (this.grid[mower.getCoordinates().getX()][mower.getCoordinates().getY()].isOccupied()) {
            throw new InvalidCellException("This cell is already used");
        }
        this.grid[mower.getCoordinates().getX()][mower.getCoordinates().getY()].occupy();
    }

    public void removeMower(Mower mower) {
        this.grid[mower.getCoordinates().getX()][mower.getCoordinates().getY()].free();
    }

    public boolean isAvailableCell(Coordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getY() <= height
                && coordinates.getX() <= width && coordinates.getY() >= 0
                && !grid[coordinates.getX()][coordinates.getY()].isOccupied();
    }
}
