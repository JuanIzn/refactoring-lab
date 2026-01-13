package com.restaurant.model.strategy;

public class StandardDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double currentTotal, int daysActive) {
        return currentTotal;
    }
}
