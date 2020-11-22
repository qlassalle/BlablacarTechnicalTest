package com.qlassalle.core.utils;

import com.qlassalle.core.exceptions.InvalidOrientationException;
import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Orientation;

import java.util.Map;

import static com.qlassalle.core.instructions.Orientation.*;

public class OrientationChange {

    private static final Map<Orientation, Orientation> RIGHT_ROTATION = Map.of(N, E, E, S, S, W, W, N);
    private static final Map<Orientation, Orientation> LEFT_ROTATION = Map.of(N, W, W, S, S, E, E, N);

    public enum Rotation implements Instruction {
        R,
        L;

        public static Rotation of(String value) {
            for (Rotation rotation : values()) {
                if (rotation.name().equals(value)) {
                    return rotation;
                }
            }

            throw new InvalidOrientationException();
        }
    }

    public static Orientation computeRotation(Rotation rotation, Orientation orientation) {
        return rotation == Rotation.L ? LEFT_ROTATION.get(orientation) : RIGHT_ROTATION.get(orientation);
    }
}
