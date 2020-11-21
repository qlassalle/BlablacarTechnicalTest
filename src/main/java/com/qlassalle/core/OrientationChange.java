package com.qlassalle.core;

import java.util.Map;

import static com.qlassalle.core.Orientation.*;

public class OrientationChange {

    private static final Map<Orientation, Orientation> RIGHT_ROTATION = Map.of(NORTH, EAST, EAST, SOUTH, SOUTH, WEST,
                                                                               WEST, NORTH);
    private static final Map<Orientation, Orientation> LEFT_ROTATION = Map.of(NORTH, WEST, WEST, SOUTH, SOUTH, EAST,
                                                                              EAST, NORTH);

    public enum Rotation {
        RIGHT,
        LEFT;
    }

    public static Orientation rotate(Rotation rotation, Orientation orientation) {
        return rotation == Rotation.LEFT ? LEFT_ROTATION.get(orientation) : RIGHT_ROTATION.get(orientation);
    }
}
