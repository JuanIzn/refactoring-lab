package com.restaurant.service;

import com.restaurant.model.Order;

public class OrderProcessor {

    public String process(Order order, boolean logDetails, boolean sendEmail, boolean generatePdf) {
        StringBuilder result = new StringBuilder();

        if (!validateOrder(order, result)) {
            return result.toString();
        }

        double total = order.calculateFinalTotal();

        appendReceiptDetails(result, order, total);
        handleNotifications(order, logDetails, sendEmail, generatePdf);

        return result.toString();
    }

    private boolean validateOrder(Order order, StringBuilder errorLog) {
        if (!order.isActive()) {
            errorLog.append("Error: Order not active");
            return false;
        }
        if (order.getName() == null || order.getName().isEmpty()) {
            errorLog.append("Error: Invalid name");
            return false;
        }
        if (order.getPrice() <= 0) { // Using 0 directly or could expose constant if public
            errorLog.append("Error: Invalid price");
            return false;
        }
        if (order.getQuantity() <= 0) {
            errorLog.append("Error: Invalid quantity");
            return false;
        }
        return true;
    }

    private void appendReceiptDetails(StringBuilder result, Order order, double total) {
        result.append("Order #").append(System.currentTimeMillis()).append("\n");
        result.append("Customer: ").append(order.getName()).append("\n");
        result.append("Email: ").append(order.getEmail()).append("\n");
        result.append("Phone: ").append(order.getPhone()).append("\n");
        result.append("Items: ").append(order.getQuantity()).append(" x $").append(order.getPrice()).append("\n");
        result.append("Total: $").append(String.format("%.2f", total)).append("\n");
    }

    private void handleNotifications(Order order, boolean logDetails, boolean sendEmail, boolean generatePdf) {
        if (logDetails) {
            if (sendEmail) {
                System.out.println("Sending email to " + order.getEmail());
            }
            if (generatePdf) {
                System.out.println("Generating PDF invoice");
            }
        }
        System.out.println("Saving to database...");
    }
}
