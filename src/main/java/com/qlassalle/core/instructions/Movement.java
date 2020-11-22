package com.qlassalle.core.instructions;

import lombok.Getter;

import java.util.Optional;

public enum Movement implements Instruction {
    F('F');

    @Getter
    private final char shortName;

    Movement(char shortName) {
        this.shortName = shortName;
    }

    public static Optional<Instruction> of(char value) {
        for (Movement movement : values()) {
            if (movement.shortName == value) {
                return Optional.of(movement);
            }
        }

        return Optional.empty();
    }
}
