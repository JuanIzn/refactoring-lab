package com.restaurant.model.strategy;

public class GoldDiscountStrategy implements DiscountStrategy {
    private static final double HIGH_VALUE_THRESHOLD = 100.0;
    private static final double MEDIUM_VALUE_THRESHOLD = 50.0;
    private static final double LONG_TERM_DISCOUNT = 0.85;
    private static final double SHORT_TERM_DISCOUNT = 0.90;
    private static final double MEDIUM_DISCOUNT = 0.95;
    private static final int DAYS_ACTIVE_THRESHOLD = 30;

    @Override
    public double applyDiscount(double currentTotal, int daysActive) {
        if (currentTotal > HIGH_VALUE_THRESHOLD) {
            return daysActive > DAYS_ACTIVE_THRESHOLD ? currentTotal * LONG_TERM_DISCOUNT : currentTotal * SHORT_TERM_DISCOUNT;
        } else if (currentTotal > MEDIUM_VALUE_THRESHOLD) {
            return currentTotal * MEDIUM_DISCOUNT;
        }
        return currentTotal;
    }
}
