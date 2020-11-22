package com.qlassalle.core.services;

import com.qlassalle.core.instructions.Instruction;
import com.qlassalle.core.models.Coordinates;
import com.qlassalle.core.models.Lawn;
import com.qlassalle.core.models.Mower;

import java.util.List;

import static com.qlassalle.core.utils.OrientationChange.Rotation;

public class LawnService {

    private final Lawn lawn;

    public LawnService(int lawnWidth, int lawnHeight, List<Mower> mowers) {
        this.lawn = new Lawn(lawnWidth, lawnHeight, mowers);
    }

//    public void addMower(Mower mower) {
//        this.lawn.addMower(mower);
//    }

    public void startMowers() {
        for (Mower mower : lawn.getMowers()) {
            applyInstruction(mower);
        }
    }

    public boolean isAvailableCell(Coordinates coordinates) {
        return lawn.isAvailableCell(coordinates);
    }

    public void moveMower(Mower mower) {
        if (!isMoveValid(mower)) {
            return;
        }
        this.lawn.removeMower(mower);
        mower.move();
        this.lawn.placeMower(mower);
    }

    private boolean isMoveValid(Mower mower) {
        return lawn.isAvailableCell(mower.computeNextMove());
    }

    public void applyInstruction(Mower mower) {
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
