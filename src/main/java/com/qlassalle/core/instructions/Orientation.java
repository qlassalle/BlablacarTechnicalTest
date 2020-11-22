package com.qlassalle.core.instructions;

import com.qlassalle.core.exceptions.InvalidOrientationException;

public enum Orientation implements Instruction {
    N,
    E,
    W,
    S;

    public static Instruction of(String value) {
        for (Orientation orientation : values()) {
            if (orientation.name().equals(value)) {
                return orientation;
            }
        }

        throw new InvalidOrientationException();
    }
}
