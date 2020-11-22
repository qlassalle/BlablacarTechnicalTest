package com.qlassalle.core.models;

import com.qlassalle.core.exceptions.InvalidOrientationException;
import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Orientation;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Queue;

import static com.qlassalle.core.utils.OrientationChange.Rotation;
import static com.qlassalle.core.utils.OrientationChange.computeRotation;

@Getter
public class Mower {

    private Coordinates coordinates;
    private Orientation orientation;
    private final Queue<Instruction> instructions;

    public Mower(int x, int y, Orientation orientation) {
        this.coordinates = new Coordinates(x, y);
        this.orientation = orientation;
        this.instructions = new ArrayDeque<>();
    }

    public Mower(int x, int y, Orientation orientation, Queue<Instruction> instructions) {
        this.coordinates = new Coordinates(x, y);
        this.orientation = orientation;
        this.instructions = instructions;
    }

    public void move() {
        this.coordinates = computeNextMove();
    }

    public Coordinates computeNextMove() {
        Coordinates futurCoordinates = new Coordinates(this.coordinates.getX(), this.coordinates.getY());
        switch(orientation) {
            case N:
                futurCoordinates.moveNorth();
                break;
            case E:
                futurCoordinates.moveEast();
                break;
            case W:
                futurCoordinates.moveWest();
                break;
            case S:
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

    @Override
    public String toString() {
        return coordinates.getX() + " " + coordinates.getY() + " " + orientation;
    }
}
