package com.restaurant.test;

import com.restaurant.model.*;
import com.restaurant.service.*;
import com.restaurant.util.*;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== Running Tests ===\n");

        testOrderCreation();
        testOrderProcessing();
        testManagerOperations();

        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }

    private static void testOrderCreation() {
        System.out.println("Testing Order Creation...");

        Order o = new OrderBuilder("John", "john@email.com")
                .phone("555-1234")
                .customerType(CustomerType.NORMAL)
                .price(10.0)
                .quantity(2)
                .country("ES")
                .active(true)
                .build();

        assertEqual(o.getName(), "John", "Order name");
        assertEqual(o.getPrice(), 10.0, "Order price");
        assertEqual(o.getQuantity(), 2, "Order quantity");
        assertEqual(o.getCountry().getCode(), "ES", "Order country");

        System.out.println();
    }

    private static void testOrderProcessing() {
        System.out.println("Testing Order Processing...");

        OrderProcessor processor = new OrderProcessor();

        Order o1 = new OrderBuilder("Alice", "alice@email.com")
                .phone("555-5678")
                .customerType(CustomerType.GOLD)
                .price(60.0)
                .quantity(2)
                .country("ES")
                .active(true)
                .build();
        String result1 = processor.process(o1, true, false, false);
        assertNotNull(result1, "Gold order processing");
        assertContains(result1, "130", "Gold order with tax");

        Order o2 = new OrderBuilder("Bob", "bob@email.com")
                .phone("555-9999")
                .customerType(CustomerType.NORMAL)
                .price(10.0)
                .quantity(1)
                .country("FR")
                .active(true)
                .build();
        String result2 = processor.process(o2, true, false, false);
        assertNotNull(result2, "Normal order processing");
        assertContains(result2, "17", "Normal order with shipping");

        Order o3 = new OrderBuilder("Charlie", "charlie@email.com")
                .phone("555-0000")
                .customerType(CustomerType.SILVER)
                .price(80.0)
                .quantity(2)
                .country("DE")
                .active(true)
                .build();
        String result3 = processor.process(o3, false, false, false);
        assertNotNull(result3, "Silver order processing");
        assertContains(result3, "175", "Silver order with discount and tax");

        System.out.println();
    }

    private static void testManagerOperations() {
        System.out.println("Testing Manager Operations...");

        Manager mgr = new Manager();
        Order mgrOrder = new OrderBuilder("David", "david@email.com")
                .phone("555-1111")
                .price(50.0)
                .quantity(2)
                .customerType(CustomerType.SILVER)
                .country("ES")
                .build();
        mgr.processOrder(mgrOrder);

        Order o = new OrderBuilder("Test", "test@email.com")
                .phone("555-0000")
                .customerType(CustomerType.SILVER)
                .price(50.0)
                .quantity(2)
                .country("ES")
                .active(true)
                .build();
        
        double total = o.calculateFinalTotal();

        assertEqual(total, 116.16, "Order tax calculation");

        String report = mgr.generateReport(ReportType.REVENUE, false, false);
        assertContains(report, "Revenue", "Manager report generation");

        System.out.println();
    }

    private static void assertEqual(Object actual, Object expected, String testName) {
        if (actual.equals(expected)) {
            System.out.println("✓ " + testName);
            passed++;
        } else {
            System.out.println("✗ " + testName + " - Expected: " + expected + ", Got: " + actual);
            failed++;
        }
    }

    private static void assertNotNull(Object obj, String testName) {
        if (obj != null) {
            System.out.println("✓ " + testName);
            passed++;
        } else {
            System.out.println("✗ " + testName + " - Expected non-null value");
            failed++;
        }
    }

    private static void assertNull(Object obj, String testName) {
        if (obj == null) {
            System.out.println("✓ " + testName);
            passed++;
        } else {
            System.out.println("✗ " + testName + " - Expected null value");
            failed++;
        }
    }

    private static void assertContains(String str, String substring, String testName) {
        if (str != null && str.contains(substring)) {
            System.out.println("✓ " + testName);
            passed++;
        } else {
            System.out.println("✗ " + testName + " - String doesn't contain: " + substring);
            failed++;
        }
    }
}
