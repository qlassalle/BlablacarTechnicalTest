package com.qlassalle.core;

import com.qlassalle.services.LawnService;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.qlassalle.core.Orientation.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MowerTest {

    private LawnService lawnService;

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerTestCases")
    void shouldMoveAMowerDependingOfItsOrientation(MowerTestCase mowerTestCase) {
        Lawn lawn = new Lawn(5, 5);
        lawnService = new LawnService(lawn, mowerTestCase.mower);
        lawnService.moveMower(mowerTestCase.mower);
        assertFalse(lawn.getZone(mowerTestCase.startX, mowerTestCase.startY).isOccupied());
        assertTrue(lawn.getZone(mowerTestCase.destX, mowerTestCase.destY).isOccupied());
    }

    private static Stream<Arguments> moveMowerTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(1, 2, NORTH, 1, 3, "Move to the north")),
                Arguments.of(new MowerTestCase(3, 3, EAST, 4, 3, "Move to the east")),
                Arguments.of(new MowerTestCase(2, 4, SOUTH, 2, 3, "Move to the south")),
                Arguments.of(new MowerTestCase(4, 1, WEST, 3, 1, "Move to the west"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerNextToWallTestCases")
    void shouldNotMoveAMowerWhenNextToAWall(MowerTestCase mowerTestCase) {
        Lawn lawn = new Lawn(5, 5);
        lawnService = new LawnService(lawn, mowerTestCase.mower);
        assertTrue(lawn.getZone(mowerTestCase.startX, mowerTestCase.startY).isOccupied());
        lawnService.moveMower(mowerTestCase.mower);
        assertTrue(lawn.getZone(mowerTestCase.destX, mowerTestCase.destY).isOccupied());
    }

    private static Stream<Arguments> moveMowerNextToWallTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(5, 5, NORTH, 5, 5, "Do not move if next to a wall at the north")),
                Arguments.of(new MowerTestCase(5, 2, EAST, 5, 2, "Do not move if next to a wall at the east")),
                Arguments.of(new MowerTestCase(1, 0, SOUTH, 1, 0, "Do not move if next to a wall at the south")),
                Arguments.of(new MowerTestCase(0, 0, WEST, 0, 0, "Do not move if next to a wall at the west"))
        );
    }

    private static class MowerTestCase {

        private int startX;
        private int startY;
        private int destX;
        private int destY;
        private final Mower mower;
        private final String displayName;

        public MowerTestCase(int startX, int startY, Orientation orientation, int destX, int destY,
                             String displayName) {
            this.startX = startX;
            this.startY = startY;
            this.destX = destX;
            this.destY = destY;
            this.mower = new Mower(startX, startY, orientation);
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
    /**
     * cant move bcs of wall
     * cant move bcs of mower
     * change orientation of mower
     */
}
