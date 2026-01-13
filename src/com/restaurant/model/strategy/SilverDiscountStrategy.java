package com.restaurant.model.strategy;

public class SilverDiscountStrategy implements DiscountStrategy {
    private static final double HIGH_VALUE_THRESHOLD = 100.0;
    private static final double SILVER_THRESHOLD = 75.0;
    private static final double HIGH_DISCOUNT = 0.92;
    private static final double MEDIUM_DISCOUNT = 0.96;

    @Override
    public double applyDiscount(double currentTotal, int daysActive) {
        if (currentTotal > HIGH_VALUE_THRESHOLD) {
            return currentTotal * HIGH_DISCOUNT;
        } else if (currentTotal > SILVER_THRESHOLD) {
            return currentTotal * MEDIUM_DISCOUNT;
        }
        return currentTotal;
    }
}
