package com.qlassalle.core;

public class Lawn {

    private final int width;
    private final int height;
    private final Cell[][] grid;

    public Lawn(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = initializeGrid(this.width + 1, this.height + 1);
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

    public Cell getZone(int x, int y) {
        // TODO: 21/11/2020 should we handle out of bounds here?
        return grid[x][y];
    }

    public void addMower(Mower mower) {
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
