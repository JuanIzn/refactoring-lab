package com.restaurant.model;

public enum CustomerType {
    NORMAL(1),
    SILVER(2),
    GOLD(3);

    private final int value;

    CustomerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CustomerType fromValue(int value) {
        for (CustomerType type : CustomerType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return NORMAL;
    }
}