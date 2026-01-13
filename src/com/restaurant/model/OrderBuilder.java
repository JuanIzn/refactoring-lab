package com.restaurant.model;

import com.restaurant.model.strategy.*;

public class OrderBuilder {
    private String name;
    private String email;
    private String phone;
    private CustomerType customerType = CustomerType.NORMAL; 
    private double price;
    private int quantity;
    private Country country = Country.OTHER; 
    private boolean active = true; 
    private int daysActive = 0;
    private DiscountStrategy discountStrategy = new StandardDiscountStrategy(); // Default

    public OrderBuilder(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public OrderBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public OrderBuilder customerType(CustomerType customerType) {
        this.customerType = customerType;
        
        switch (customerType) {
            case GOLD:
                this.discountStrategy = new GoldDiscountStrategy();
                break;
            case SILVER:
                this.discountStrategy = new SilverDiscountStrategy();
                break;
            default:
                this.discountStrategy = new StandardDiscountStrategy();
        }
        return this;
    }
    
    public OrderBuilder withStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
        return this;
    }

    public OrderBuilder price(double price) {
        this.price = price;
        return this;
    }

    public OrderBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder country(Country country) {
        this.country = country;
        return this;
    }
    
    public OrderBuilder country(String countryCode) {
        this.country = Country.fromCode(countryCode);
        return this;
    }

    public OrderBuilder active(boolean active) {
        this.active = active;
        return this;
    }
    
    public OrderBuilder daysActive(int daysActive) {
        this.daysActive = daysActive;
        return this;
    }

    public Order build() {
        return new Order(this);
    }

    String getName() { return name; }
    String getEmail() { return email; }
    String getPhone() { return phone; }
    CustomerType getCustomerType() { return customerType; }
    double getPrice() { return price; }
    int getQuantity() { return quantity; }
    Country getCountry() { return country; }
    boolean isActive() { return active; }
    int getDaysActive() { return daysActive; }
    DiscountStrategy getDiscountStrategy() { return discountStrategy; }
}
