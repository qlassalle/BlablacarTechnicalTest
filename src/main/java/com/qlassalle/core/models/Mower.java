package com.qlassalle.core.models;

import com.qlassalle.core.instructions.Orientation;
import com.qlassalle.core.exceptions.InvalidOrientationException;
import lombok.Getter;

import static com.qlassalle.core.utils.OrientationChange.*;

@Getter
public class Mower {

    private Coordinates coordinates;
    private Orientation orientation;

    public Mower(int x, int y, Orientation orientation) {
        this.coordinates = new Coordinates(x, y);
        this.orientation = orientation;
    }

    public void move() {
        this.coordinates = computeNextMove();
    }

    public Coordinates computeNextMove() {
        Coordinates futurCoordinates = new Coordinates(this.coordinates.getX(), this.coordinates.getY());
        switch(orientation) {
            case NORTH:
                futurCoordinates.moveNorth();
                break;
            case EAST:
                futurCoordinates.moveEast();
                break;
            case WEST:
                futurCoordinates.moveWest();
                break;
            case SOUTH:
                futurCoordinates.moveSouth();
                break;
            default:
                // keep a default here because if someone adds a new orientation without implementing its logic,
                // he'll know rapidly that he has to.
                throw new InvalidOrientationException();
        }
        return futurCoordinates;
    }

    public void changeOrientation(Rotation rotation) {
        this.orientation = computeRotation(rotation, orientation);
    }
}
