package com.qlassalle.core;

import com.qlassalle.services.LawnService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static com.qlassalle.core.Movement.F;
import static com.qlassalle.core.Orientation.*;
import static com.qlassalle.core.OrientationChange.Rotation.L;
import static com.qlassalle.core.OrientationChange.Rotation.R;
import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

    private LawnService lawnService;

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerTestCases")
    void shouldMoveAMowerDependingOfItsOrientation(MowerTestCase mowerTestCase) {
        Lawn lawn = new Lawn(5, 5);
        lawnService = new LawnService(lawn, mowerTestCase.mower);
        lawnService.moveMower(mowerTestCase.mower);
        assertFalse(lawn.getCell(mowerTestCase.startX, mowerTestCase.startY).isOccupied());
        assertTrue(lawn.getCell(mowerTestCase.destX, mowerTestCase.destY).isOccupied());
    }

    private static Stream<Arguments> moveMowerTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(1, 2, NORTH, 1, 3, NORTH, "Move to the north")),
                Arguments.of(new MowerTestCase(3, 3, EAST, 4, 3, EAST, "Move to the east")),
                Arguments.of(new MowerTestCase(2, 4, SOUTH, 2, 3, SOUTH, "Move to the south")),
                Arguments.of(new MowerTestCase(4, 1, WEST, 3, 1, WEST, "Move to the west"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerNextToWallTestCases")
    void shouldNotMoveAMowerWhenNextToAWall(MowerTestCase mowerTestCase) {
        Lawn lawn = new Lawn(5, 5);
        lawnService = new LawnService(lawn, mowerTestCase.mower);
        assertTrue(lawn.getCell(mowerTestCase.startX, mowerTestCase.startY).isOccupied());
        lawnService.moveMower(mowerTestCase.mower);
        assertTrue(lawn.getCell(mowerTestCase.destX, mowerTestCase.destY).isOccupied());
    }

    private static Stream<Arguments> moveMowerNextToWallTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(5, 5, NORTH, 5, 5, NORTH, "Do not move if next to a wall at the north")),
                Arguments.of(new MowerTestCase(5, 2, EAST, 5, 2, EAST, "Do not move if next to a wall at the east")),
                Arguments.of(new MowerTestCase(1, 0, SOUTH, 1, 0, SOUTH, "Do not move if next to a wall at the south")),
                Arguments.of(new MowerTestCase(0, 0, WEST, 0, 0, WEST,"Do not move if next to a wall at the west"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerWithChangeOfOrientationTestCases")
    void shouldMoveAMowerWithChangeOfOrientation(MowerTestCase mowerTestCase) {
        Lawn lawn = new Lawn(5, 5);
        Mower mower = mowerTestCase.mower;
        lawnService = new LawnService(lawn, mower);
        lawnService.applyInstruction(mowerTestCase.instructions, mower);
        assertEquals(mower.getOrientation(), mowerTestCase.finalOrientation);
    }

    private static Stream<Arguments> moveMowerWithChangeOfOrientationTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(2, 1, NORTH, 3, 1, List.of(R, F), EAST,
                                               "Change orientation to the right once and move forward")),
                Arguments.of(new MowerTestCase(2,1, EAST, 2, 2, List.of(L, F), NORTH,
                                               "Change orientation to the left once and move forward")),
                Arguments.of(new MowerTestCase(4, 2, SOUTH, 4, 4, List.of(R, R, F, F), NORTH,
                                               "Change orientation to the right twice and move forward twice")),
                Arguments.of(new MowerTestCase(4, 4, WEST, 5, 4, List.of(L, L, F, F), EAST,
                                               "Change orientation to the left twice and move forward only once because" +
                                                       " of a wall")),
                Arguments.of(new MowerTestCase(1, 2, NORTH, 1, 3, List.of(L, F, L, F, L, F, L, F, F), NORTH,
                                               "1st Example implementation")),
                Arguments.of(new MowerTestCase(3, 3, EAST, 5, 1, List.of(F, F, R, F, F, R, F, R, R, F), EAST,
                                               "2nd Example implementation"))
        );
    }

    @DisplayName("Should move because of another mower")
    @Test
    void shouldNotMoveBecauseOfAnotherMower() {
        Lawn lawn = new Lawn(5, 5);
        Mower mowerOne = new Mower(2, 2, EAST);
        Mower mowerTwo = new Mower(3, 2, NORTH);
        lawnService = new LawnService(lawn, mowerOne, mowerTwo);
        lawnService.applyInstruction(new ArrayDeque<>(List.of(F)), mowerOne);
        assertEquals(2, mowerOne.getCoordinates().getX());
        assertEquals(2, mowerOne.getCoordinates().getY());
    }

    private static class MowerTestCase {

        private final int startX;
        private final int startY;
        private final int destX;
        private final int destY;
        private final Mower mower;
        private final String displayName;
        private final Queue<Instruction> instructions;
        private final Orientation finalOrientation;

        public MowerTestCase(int startX, int startY, Orientation orientation, int destX, int destY,
                             Orientation finalOrientation, String displayName) {
            this.startX = startX;
            this.startY = startY;
            this.destX = destX;
            this.destY = destY;
            this.mower = new Mower(startX, startY, orientation);
            this.displayName = displayName;
            this.instructions = new ArrayDeque<>();
            this.finalOrientation = finalOrientation;
        }

        public MowerTestCase(int startX, int startY, Orientation orientation, int destX, int destY,
                             List<Instruction> instructions, Orientation finalOrientation, String displayName) {
            this.startX = startX;
            this.startY = startY;
            this.destX = destX;
            this.destY = destY;
            this.mower = new Mower(startX, startY, orientation);
            this.displayName = displayName;
            this.instructions = new ArrayDeque<>(instructions);
            this.finalOrientation = finalOrientation;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
