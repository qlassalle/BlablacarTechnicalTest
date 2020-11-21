package com.qlassalle.services;

import com.qlassalle.core.Lawn;
import com.qlassalle.core.Mower;

public class LawnService {

    private final Lawn lawn;

    public LawnService(Lawn lawn, Mower mower) {
        this.lawn = lawn;
        this.lawn.addMower(mower);
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
}
