package com.qlassalle.core;

public class Cell {
    private final int x;
    private final int y;
    private boolean occupied;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void free() {
        this.occupied = false;
    }
}
