package com.qlassalle.services;

import com.qlassalle.core.Instruction;
import com.qlassalle.core.Lawn;
import com.qlassalle.core.Mower;

import java.util.Queue;

import static com.qlassalle.core.OrientationChange.Rotation;

public class LawnService {

    private final Lawn lawn;

    public LawnService(Lawn lawn, Mower... mowers) {
        this.lawn = lawn;
        for (Mower mower : mowers) {
            this.lawn.addMower(mower);
        }
    }

//    public void addMower(Mower mower) {
//        this.lawn.addMower(mower);
//    }

    public void moveMower(Mower mower) {
        if (!isMoveValid(mower)) {
            return;
        }
        this.lawn.removeMower(mower);
        mower.move();
        this.lawn.addMower(mower);
    }

    private boolean isMoveValid(Mower mower) {
        return lawn.isAvailableCell(mower.computeNextMove());
    }

    public void applyInstruction(Queue<Instruction> instructions, Mower mower) {
        instructions.forEach(instruction -> applyInstruction(instruction, mower));
    }

    private void applyInstruction(Instruction instruction, Mower mower) {
        if (instruction instanceof Rotation) {
            mower.changeOrientation((Rotation) instruction);
        } else {
            moveMower(mower);
        }
    }
}
