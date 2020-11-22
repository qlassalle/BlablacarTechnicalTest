package com.qlassalle.core.services;

import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.models.Coordinates;
import com.qlassalle.core.models.Lawn;
import com.qlassalle.core.models.Mower;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.qlassalle.core.utils.OrientationChange.Rotation;

public class LawnService {

    private final Lawn lawn;

    public LawnService(int lawnWidth, int lawnHeight, List<Mower> mowers) {
        this.lawn = new Lawn(lawnWidth, lawnHeight, mowers);
    }

    public void startMowers() {
        ExecutorService executorService = Executors.newFixedThreadPool(lawn.getMowers().size());
        for (Mower mower : lawn.getMowers()) {
            Runnable runnable = () -> applyInstruction(mower);
            executorService.submit(runnable);
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Unable to stop thread, stacktrace is print below");
            e.printStackTrace();
            executorService.shutdownNow();
        }
    }

    boolean isAvailableCell(Coordinates coordinates) {
        return lawn.isAvailableCell(coordinates);
    }

    private void moveMower(Mower mower) {
        // fail-fast if move isn't valid
        if (!isMoveValid(mower)) {
            return;
        }
        // first acquire lock on current position
        synchronized (lawn.getCell(mower.getCoordinates())) {
            // then acquire lock on target position
            synchronized (lawn.getCell(mower.computeNextMove())) {
                // check again after acquiring lock if no mower took this place
                if (!isMoveValid(mower)) {
                    return;
                }
                this.lawn.removeMower(mower);
                mower.move();
                this.lawn.placeMower(mower);
            }
        }
    }

    private boolean isMoveValid(Mower mower) {
        return lawn.isAvailableCell(mower.computeNextMove());
    }

    private void applyInstruction(Mower mower) {
        mower.getInstructions().forEach(instruction -> applyInstruction(instruction, mower));
    }

    private void applyInstruction(Instruction instruction, Mower mower) {
        if (instruction instanceof Rotation) {
            mower.changeOrientation((Rotation) instruction);
        } else {
            moveMower(mower);
        }
    }
}
