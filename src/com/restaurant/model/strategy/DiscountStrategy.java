package com.restaurant.model.strategy;

public interface DiscountStrategy {
    double applyDiscount(double currentTotal, int daysActive);
}
