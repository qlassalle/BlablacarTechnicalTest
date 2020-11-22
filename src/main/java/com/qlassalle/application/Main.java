package com.qlassalle.application;

public class Main {

    public static void main(String[] args) {
        LawnController controller = new LawnController();
        controller.process("input/two_mowers.txt");
    }
}
