package com.qlassalle.core.utils;

import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Orientation;

import java.util.Map;
import java.util.Optional;

import static com.qlassalle.core.instructions.Orientation.*;

public class OrientationChange {

    private static final Map<Orientation, Orientation> RIGHT_ROTATION = Map.of(N, E, E, S, S, W, W, N);
    private static final Map<Orientation, Orientation> LEFT_ROTATION = Map.of(N, W, W, S, S, E, E, N);

    public enum Rotation implements Instruction {
        R('R'),
        L('L');

        private final char shortName;

        Rotation(char shortName) {
            this.shortName = shortName;
        }

        public static Optional<Instruction> of(char value) {
            for (Rotation rotation : values()) {
                if (rotation.shortName == value) {
                    return Optional.of(rotation);
                }
            }

            return Optional.empty();
        }
    }

    public static Orientation computeRotation(Rotation rotation, Orientation orientation) {
        return rotation == Rotation.L ? LEFT_ROTATION.get(orientation) : RIGHT_ROTATION.get(orientation);
    }
}
