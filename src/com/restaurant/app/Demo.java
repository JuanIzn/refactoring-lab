package com.restaurant.app;

import com.restaurant.model.*;
import com.restaurant.service.*;

public class Demo {

        public static void main(String[] args) {
                System.out.println("=== RESTAURANT ORDER SYSTEM DEMO ===\n");

                OrderProcessor processor = new OrderProcessor();


                Order order1 = new OrderBuilder("John Smith", "john@email.com")
                                .phone("555-1234")
                                .customerType(CustomerType.NORMAL)
                                .price(25.0)
                                .quantity(2)
                                .country("ES")
                                .active(true)
                                .build();
                System.out.println(processor.process(order1, true, true, false));


                Order order2 = new OrderBuilder("Mary VIP", "mary@email.com")
                                .phone("555-5678")
                                .customerType(CustomerType.GOLD)
                                .price(60.0)
                                .quantity(2)
                                .country("ES")
                                .active(true)
                                .daysActive(35) // Old customer
                                .build();
                System.out.println(processor.process(order2, false, false, true));

                // Use Manager to handle orders
                Manager manager = new Manager();

                Order managerOrder1 = new OrderBuilder("Customer 1", "customer1@email.com")
                                .phone("555-1111")
                                .price(30.0)
                                .quantity(2)
                                .customerType(CustomerType.NORMAL)
                                .country("ES")
                                .build();
                manager.processOrder(managerOrder1);

                // VIP Customer with extras (Express +9.99, Gift +2.99, Insurance +4.99 = +17.97)
                // Base price was 150.0, adding extras to base price for simplicity in this demo
                Order managerOrder2 = new OrderBuilder("VIP Customer", "vip@email.com")
                                .phone("555-2222")
                                .price(167.97) 
                                .quantity(3)
                                .customerType(CustomerType.GOLD)
                                .country("ES")
                                .build();
                manager.processOrder(managerOrder2);

                // statistics and reports
                manager.stats(true, false, false);
                System.out.println(manager.generateReport(ReportType.CUSTOMER_COUNTS, false, false));

                // Validate and process order
                Order order3 = new OrderBuilder("Test Customer", "test@email.com")
                                .phone("555-9999")
                                .customerType(CustomerType.NORMAL)
                                .price(100.0)
                                .quantity(2)
                                .country("ES")
                                .active(true)
                                .build();
                manager.validateAndProcess(order3, OrderAction.PROCESS_NORMALLY);

                System.out.println("\n=== END OF DEMO ===");
        }
}
