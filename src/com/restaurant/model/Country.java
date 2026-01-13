package com.restaurant.model;

public enum Country {
    SPAIN("ES", 1.21),
    FRANCE("FR", 1.20),
    GERMANY("DE", 1.19),
    UK("UK", 1.20),
    OTHER("OTHER", 1.15);

    private final String code;
    private final double taxRate;

    Country(String code, double taxRate) {
        this.code = code;
        this.taxRate = taxRate;
    }

    public String getCode() {
        return code;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public static Country fromCode(String code) {
        for (Country country : Country.values()) {
            if (country.code.equalsIgnoreCase(code)) {
                return country;
            }
        }
        return OTHER;
    }
}