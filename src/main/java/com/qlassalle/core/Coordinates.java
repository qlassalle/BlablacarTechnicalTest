package com.qlassalle.core;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveNorth() {
        ++this.y;
    }

    public void moveEast() {
        ++this.x;
    }

    public void moveWest() {
        --this.x;
    }

    public void moveSouth() {
        --this.y;
    }
}
