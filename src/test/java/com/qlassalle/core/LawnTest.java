package com.qlassalle.core;

import com.qlassalle.core.models.Lawn;
import com.qlassalle.core.models.Mower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.qlassalle.core.instructions.Orientation.NORTH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LawnTest {

    @DisplayName("Should create a lawn with one mower")
    @Test
    void shouldCreateALawnWithOnemower() {
        Lawn lawn = new Lawn(5, 5);
        assertFalse(lawn.getCell(1, 2).isOccupied());
        Mower mower = new Mower(1, 2, NORTH);
        lawn.addMower(mower);
        assertTrue(lawn.getCell(1, 2).isOccupied());
    }
}
