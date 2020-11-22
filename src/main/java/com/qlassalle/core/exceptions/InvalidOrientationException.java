package com.qlassalle.core.exceptions;

public class InvalidOrientationException extends RuntimeException {
    public InvalidOrientationException() {
        super("This orientation is not implemented.");
    }
}
