package com.qlassalle.core;

import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Orientation;
import com.qlassalle.core.models.Coordinates;
import com.qlassalle.core.models.Mower;
import com.qlassalle.core.services.LawnService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Stream;

import static com.qlassalle.core.instructions.Movement.F;
import static com.qlassalle.core.instructions.Orientation.*;
import static com.qlassalle.core.utils.OrientationChange.Rotation.L;
import static com.qlassalle.core.utils.OrientationChange.Rotation.R;
import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

    private LawnService lawnService;
    private final int lawnWidthDimension = 5;
    private final int lawnHeightDimension = 5;

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerTestCases")
    void shouldMoveAMowerDependingOfItsOrientation(MowerTestCase mowerTestCase) {
        lawnService = new LawnService(lawnWidthDimension, lawnHeightDimension, List.of(mowerTestCase.mower));
        lawnService.moveMower(mowerTestCase.mower);
        assertTrue(lawnService.isAvailableCell(new Coordinates(mowerTestCase.startX, mowerTestCase.startY)));
        assertFalse(lawnService.isAvailableCell(new Coordinates(mowerTestCase.destX, mowerTestCase.destY)));
    }

    private static Stream<Arguments> moveMowerTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(1, 2, N, 1, 3, N, "Move to the north")),
                Arguments.of(new MowerTestCase(3, 3, E, 4, 3, E, "Move to the east")),
                Arguments.of(new MowerTestCase(2, 4, S, 2, 3, S, "Move to the south")),
                Arguments.of(new MowerTestCase(4, 1, W, 3, 1, W, "Move to the west"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerNextToWallTestCases")
    void shouldNotMoveAMowerWhenNextToAWall(MowerTestCase mowerTestCase) {
        lawnService = new LawnService(lawnWidthDimension, lawnHeightDimension, List.of(mowerTestCase.mower));
        assertFalse(lawnService.isAvailableCell(new Coordinates(mowerTestCase.startX, mowerTestCase.startY)));
        lawnService.moveMower(mowerTestCase.mower);
        assertFalse(lawnService.isAvailableCell(new Coordinates(mowerTestCase.destX, mowerTestCase.destY)));
    }

    private static Stream<Arguments> moveMowerNextToWallTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(5, 5, N, 5, 5, N, "Do not move if next to a wall at the north")),
                Arguments.of(new MowerTestCase(5, 2, E, 5, 2, E, "Do not move if next to a wall at the east")),
                Arguments.of(new MowerTestCase(1, 0, S, 1, 0, S, "Do not move if next to a wall at the south")),
                Arguments.of(new MowerTestCase(0, 0, W, 0, 0, W, "Do not move if next to a wall at the west"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("moveMowerWithChangeOfOrientationTestCases")
    void shouldMoveAMowerWithChangeOfOrientation(MowerTestCase mowerTestCase) {
        Mower mower = mowerTestCase.mower;
        lawnService = new LawnService(lawnWidthDimension, lawnHeightDimension, List.of(mower));
//        lawnService.applyInstruction(mowerTestCase.instructions, mower);
        lawnService.applyInstruction(mower);
        assertEquals(mower.getOrientation(), mowerTestCase.finalOrientation);
    }

    private static Stream<Arguments> moveMowerWithChangeOfOrientationTestCases() {
        return Stream.of(
                Arguments.of(new MowerTestCase(2, 1, N, 3, 1, List.of(R, F), E,
                                               "Change orientation to the right once and move forward")),
                Arguments.of(new MowerTestCase(2, 1, E, 2, 2, List.of(L, F), N,
                                               "Change orientation to the left once and move forward")),
                Arguments.of(new MowerTestCase(4, 2, S, 4, 4, List.of(R, R, F, F), N,
                                               "Change orientation to the right twice and move forward twice")),
                Arguments.of(new MowerTestCase(4, 4, W, 5, 4, List.of(L, L, F, F), E,
                                               "Change orientation to the left twice and move forward only once because" +
                                                       " of a wall")),
                Arguments.of(new MowerTestCase(1, 2, N, 1, 3, List.of(L, F, L, F, L, F, L, F, F), N,
                                               "1st Example implementation")),
                Arguments.of(new MowerTestCase(3, 3, E, 5, 1, List.of(F, F, R, F, F, R, F, R, R, F), E,
                                               "2nd Example implementation"))
        );
    }

    @DisplayName("Should not move because of another mower")
    @Test
    void shouldNotMoveBecauseOfAnotherMower() {
        Mower mowerOne = new Mower(2, 2, E, new ArrayDeque<>(List.of(F)));
        Mower mowerTwo = new Mower(3, 2, N);
        lawnService = new LawnService(lawnWidthDimension, lawnHeightDimension, List.of(mowerOne, mowerTwo));
        lawnService.applyInstruction(mowerOne);
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
        private final Orientation finalOrientation;

        public MowerTestCase(int startX, int startY, Orientation orientation, int destX, int destY,
                             Orientation finalOrientation, String displayName) {
            this.startX = startX;
            this.startY = startY;
            this.destX = destX;
            this.destY = destY;
            this.mower = new Mower(startX, startY, orientation);
            this.displayName = displayName;
            this.finalOrientation = finalOrientation;
        }

        public MowerTestCase(int startX, int startY, Orientation orientation, int destX, int destY,
                             List<Instruction> instructions, Orientation finalOrientation, String displayName) {
            this.startX = startX;
            this.startY = startY;
            this.destX = destX;
            this.destY = destY;
            this.mower = new Mower(startX, startY, orientation, new ArrayDeque<>(instructions));
            this.displayName = displayName;
            this.finalOrientation = finalOrientation;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
