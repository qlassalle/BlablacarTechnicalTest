package com.qlassalle.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class LawnControllerTest {

    private LawnController lawnController = new LawnController();

    @DisplayName("Should create lawn and process instructions coming from input file")
    @Test
    void shouldCreateLawnAndProcessInstructionsComingFromInputFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceAsStream = classLoader.getResourceAsStream("input/two_mowers.txt");
        Queue<String> mowerFinalPositions = lawnController.process(resourceAsStream);
        assertIterableEquals(List.of("1 3 N", "5 1 E"), mowerFinalPositions);
    }

    @DisplayName("Should create lawn and process instructions for two mowers hitting")
    @Test
    void shouldCreateLawnAndProcessInstructionsComingFromInputFileWithTwoMowersHitting() {
        ClassLoader classLoader = getClass().getClassLoader();
        var resourceAsStream = classLoader.getResourceAsStream("input/two_mowers_hitting.txt");
        Queue<String> mowerFinalPositions = lawnController.process(resourceAsStream);
        assertIterableEquals(List.of("4 4 E", "3 3 E"), mowerFinalPositions);
    }
}
