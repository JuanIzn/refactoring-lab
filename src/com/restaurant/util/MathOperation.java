package com.restaurant.util;

public enum MathOperation {
    ADD(1),
    SUBTRACT(2),
    MULTIPLY(3),
    DIVIDE(4);

    private final int code;

    MathOperation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MathOperation fromCode(int code) {
        for (MathOperation op : MathOperation.values()) {
            if (op.code == code) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid operation code: " + code);
    }
}