package com.qlassalle.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LawnMowerInputFileReaderTest {

    @DisplayName("Should read an input file")
    @Test
    void shouldReadAnInputFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        var fileAsList = LawnMowerInputFileReader.readAsList("input/two_mowers.txt");
        assertEquals(List.of("5 5", "1 2 N", "LFLFLFLFF", "3 3 E", "FFRFFRFRRF"), fileAsList);
    }
}
