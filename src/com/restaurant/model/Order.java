package com.restaurant.model;

import com.restaurant.model.strategy.DiscountStrategy;
import com.restaurant.model.strategy.StandardDiscountStrategy;

public class Order {

    // Constants
    private static final double MIN_ORDER_AMOUNT = 0.0;
    private static final double MIN_QUANTITY = 0;
    private static final double SHIPPING_THRESHOLD = 25.0;
    private static final double SHIPPING_COST = 5.99;
    private static final int MAX_DAYS_ACTIVE = 365;

    private String name;
    private String email;
    private String phone;
    private CustomerType customerType;
    private double price;
    private int quantity;
    private Country country;
    private boolean active;
    private int daysActive;
    private DiscountStrategy discountStrategy;

    Order(OrderBuilder builder) {
        this.name = builder.getName();
        this.email = builder.getEmail();
        this.phone = builder.getPhone();
        this.customerType = builder.getCustomerType();
        this.price = builder.getPrice();
        this.quantity = builder.getQuantity();
        this.country = builder.getCountry();
        this.active = builder.isActive();
        this.daysActive = builder.getDaysActive();
        this.discountStrategy = builder.getDiscountStrategy();
    }
    
    public double calculateFinalTotal() {
        double total = price * quantity;
        total = applyCustomerDiscount(total);
        total = applyTax(total);
        total = applyShipping(total);
        return total;
    }

    private double applyCustomerDiscount(double currentTotal) {
        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(currentTotal, daysActive);
        }
        return currentTotal;
    }

    private double applyTax(double currentTotal) {
        return currentTotal * country.getTaxRate();
    }

    private double applyShipping(double currentTotal) {
        if (currentTotal < SHIPPING_THRESHOLD) {
            return currentTotal + SHIPPING_COST;
        }
        return currentTotal;
    }

    public double calculateTotal() {
        return price * quantity;
    }

    public void updateDaysActive() {
        daysActive++;
        if (daysActive > MAX_DAYS_ACTIVE) {
            active = false;
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public CustomerType getCustomerType() { return customerType; }
    public void setCustomerType(CustomerType customerType) { this.customerType = customerType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getDaysActive() { return daysActive; }
    public void setDaysActive(int daysActive) { this.daysActive = daysActive; }
}
