package com.qlassalle.application;

import com.qlassalle.application.exceptions.InputFileProcessingException;
import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.instructions.Orientation;
import com.qlassalle.core.models.Mower;
import com.qlassalle.core.services.LawnService;

import java.util.*;
import java.util.stream.Collectors;

public class LawnController {

    public Queue<String> process(String fileName) {
        Queue<String> instructions = LawnMowerInputFileReader.readAsList(fileName);
        if (instructions.isEmpty()) {
            throw new InputFileProcessingException("Instructions must be present");
        }

        List<Integer> lawnDimensions = LawnMowerInputFileReader.convertStringToIntegerList(instructions.poll());
        List<Mower> mowers = initializeMowers(instructions);

        LawnService lawnService = new LawnService(lawnDimensions.get(0), lawnDimensions.get(1), mowers);
        lawnService.startMowers();

        mowers.forEach(System.out::println);
        return mowers.stream()
                     .map(Mower::toString)
                     .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private List<Mower> initializeMowers(Queue<String> instructions) {
        List<Mower> mowers = new ArrayList<>();
        while (!instructions.isEmpty()) {
            List<String> mowerDetails = Arrays.asList(instructions.poll().split(" "));
            Queue<Instruction> mowerInstructions =
                    LawnMowerInputFileReader.convertStringToInstructionList(Objects.requireNonNull(instructions.poll()));
            mowers.add(new Mower(Integer.parseInt(mowerDetails.get(0)),
                                 Integer.parseInt(mowerDetails.get(1)),
                                 (Orientation) Orientation.of(mowerDetails.get(2)),
                                 mowerInstructions));
        }

        return mowers;
    }
}
