package com.qlassalle.core.models;

public class Cell {
    private boolean occupied;

    public Cell() {
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
