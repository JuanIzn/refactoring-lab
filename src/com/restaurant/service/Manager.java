package com.restaurant.service;

import com.restaurant.model.*;
import java.util.*;

public class Manager {
    private List<Order> orders = new ArrayList<>();
    private double revenue = 0;

    public void processOrder(Order order) {
        if (order == null) return;
        
        orders.add(order);
        
        
        revenue += order.calculateFinalTotal();
    }

    public void save(Order order, boolean email, boolean pdf, boolean backup) {
        if (order == null || !order.isActive())
            return;

        double total = order.calculateFinalTotal();

        System.out.println("INSERT INTO orders VALUES ('" + order.getName() + "', " + total + ")");

        if (email) {
            System.out.println("EMAIL: Order confirmed for " + order.getName());
            System.out.println("To: " + order.getEmail());
            System.out.println("Total: $" + String.format("%.2f", total));
        }

        if (pdf) {
            System.out.println("PDF: Generating invoice...");
            System.out.println("Customer: " + order.getName());
            System.out.println("Amount: $" + String.format("%.2f", total));
        }

        if (backup) {
            System.out.println("BACKUP: Saving to backup server...");
        }
    }

    public String generateReport(ReportType type, boolean detailed, boolean includeInactive) {
        StringBuilder report = new StringBuilder();

        if (type == ReportType.REVENUE) {
            if (detailed) {
                for (Order order : orders) {
                    if (order.isActive() || includeInactive) {
                        report.append("Order: ").append(order.getName())
                              .append(" - $").append(String.format("%.2f", order.calculateFinalTotal()))
                              .append("\n");
                    }
                }
            } else {
                report.append("Total Revenue: $").append(String.format("%.2f", revenue));
            }
        } else if (type == ReportType.CUSTOMER_COUNTS) {
            generateCustomerCountReport(report, detailed);
        }

        return report.toString();
    }

    private void generateCustomerCountReport(StringBuilder report, boolean detailed) {
        if (detailed) {
            for (Order order : orders) {
                report.append(order.getCustomerType()).append(": ").append(order.getName()).append("\n");
            }
        } else {
            int gold = 0, silver = 0, normal = 0;
            for (Order order : orders) {
                if (order.getCustomerType() == CustomerType.GOLD) gold++;
                else if (order.getCustomerType() == CustomerType.SILVER) silver++;
                else normal++;
            }
            report.append("Gold: ").append(gold)
                  .append(", Silver: ").append(silver)
                  .append(", Normal: ").append(normal);
        }
    }

    public void stats(boolean print, boolean save, boolean email) {
        if (orders.isEmpty()) {
            if (print) System.out.println("No orders to show stats for.");
            return;
        }

        double sum = 0;
        double max = 0;
        double min = Double.MAX_VALUE;

        for (Order order : orders) {
            double t = order.calculateFinalTotal();
            sum += t;
            if (t > max) max = t;
            if (t < min) min = t;
        }

        double avg = sum / orders.size();

        if (print) {
            System.out.println("=== STATISTICS ===");
            System.out.println("Average: $" + String.format("%.2f", avg));
            System.out.println("Max: $" + String.format("%.2f", max));
            System.out.println("Min: $" + String.format("%.2f", min));
            System.out.println("Total Orders: " + orders.size());
        }

        if (save) System.out.println("Saving statistics to file...");
        if (email) System.out.println("Emailing statistics to admin@company.com");
    }

    public boolean validateAndProcess(Order order, OrderAction action) {
        if (order == null || order.getName() == null || order.getName().isEmpty() ||
            order.getEmail() == null || order.getEmail().isEmpty() ||
            order.getPrice() <= 0 || order.getQuantity() <= 0) {
            return false;
        }

        double total = order.calculateFinalTotal();

        switch (action) {
            case PROCESS_NORMALLY:
                orders.add(order);
                revenue += total;
                System.out.println("Order processed normally");
                return true;
            case PROCESS_URGENT:
                orders.add(0, order);
                // Urgent orders might have a fee, but for now we just add it
                // Logic for fees should be in Order/Builder, not here.
                revenue += total; 
                System.out.println("URGENT order processed");
                return true;
            case SEND_FOR_REVIEW:
                System.out.println("Order sent for review");
                if (total > 500) {
                    System.out.println("High value - needs approval");
                    return false;
                }
                orders.add(order);
                revenue += total;
                return true;
            default:
                return false;
        }
    }
}