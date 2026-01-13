package com.restaurant.model;

public enum ReportType {
    REVENUE(1),
    CUSTOMER_COUNTS(2);

    private final int code;

    ReportType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ReportType fromCode(int code) {
        for (ReportType type : ReportType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return REVENUE;
    }
}