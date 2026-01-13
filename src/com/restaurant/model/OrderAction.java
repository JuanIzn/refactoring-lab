package com.restaurant.model;

public enum OrderAction {
    PROCESS_NORMALLY(1),
    PROCESS_URGENT(2),
    SEND_FOR_REVIEW(3);

    private final int code;

    OrderAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderAction fromCode(int code) {
        for (OrderAction action : OrderAction.values()) {
            if (action.code == code) {
                return action;
            }
        }
        return PROCESS_NORMALLY;
    }
}