package com.qlassalle.core.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Coordinates {
    private int x;
    private int y;

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

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}
