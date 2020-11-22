package com.qlassalle.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class LawnControllerTest {

    private final LawnController lawnController = new LawnController();

    @DisplayName("Should create lawn and process instructions coming from input file")
    @RepeatedTest(1000)
    void shouldCreateLawnAndProcessInstructionsComingFromInputFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceAsStream = classLoader.getResourceAsStream("input/two_mowers.txt");
        Queue<String> mowerFinalPositions = lawnController.process(resourceAsStream);
        assertIterableEquals(List.of("1 3 N", "5 1 E"), mowerFinalPositions);
    }

    @DisplayName("Should create lawn and process instructions for two mowers hitting")
    @RepeatedTest(1000)
    void shouldCreateLawnAndProcessInstructionsComingFromInputFileWithTwoMowersHitting() {
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceAsStream = classLoader.getResourceAsStream("input/two_mowers_hitting.txt");
        Queue<String> mowerFinalPositions = lawnController.process(resourceAsStream);
        assertIterableEquals(List.of("4 4 E", "3 3 E"), mowerFinalPositions);
    }

    @DisplayName("Lot of mowers little space")
    @RepeatedTest(10)
    /**
     * This test is mainly used to ensure that mowers don't hit another mower. This test can be ran with debug to see
     * if threads are really launched and locks correctly used.
     */
    void lotOfMowersLittleSpace() {
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceAsStream = classLoader.getResourceAsStream("input/lot_of_mowers.txt");
        Queue<String> mowerFinalPositions = lawnController.process(resourceAsStream);
    }
}
