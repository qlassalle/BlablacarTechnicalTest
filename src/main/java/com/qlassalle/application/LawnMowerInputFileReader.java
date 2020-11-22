package com.qlassalle.application;

import com.qlassalle.application.exceptions.InputFileProcessingException;
import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Movement;
import com.qlassalle.core.utils.OrientationChange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LawnMowerInputFileReader {

    private LawnMowerInputFileReader() {
        throw new UnsupportedOperationException("Utility method should not be instantiated");
    }

    public static Queue<String> readAsList(InputStream resourceAsStream) {
        Queue<String> fileAsList = new LinkedList<>(); // to keep order of insertion

        try (resourceAsStream) {
            InputStreamReader reader = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
            BufferedReader breader = new BufferedReader(reader);
            String line;
            while ((line = breader.readLine()) != null) {
                fileAsList.add(line);
            }
        } catch (IOException e) {
            throw new InputFileProcessingException("Unable to read input file");
        }

        return fileAsList;
    }

    public static List<Integer> convertStringToIntegerList(String text) {
        String[] splittedInstructions = text.split(" ");
        List<Integer> result = new LinkedList<>();
        for (String splittedInstruction : splittedInstructions) {
            result.add(Integer.parseInt(splittedInstruction));
        }

        return result;
    }

    public static Queue<Instruction> convertStringToInstructionList(String text) {
        Queue<Instruction> instructions = new LinkedList<>();
        for (char character : text.toCharArray()) {
            if (Movement.F.getShortName() == character) {
                instructions.add(Movement.F);
            } else {
                instructions.add(OrientationChange.Rotation.of(character).get());
            }
        }

        return instructions;
    }
}
