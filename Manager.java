import java.util.*;

public class Manager {
    private List<Order> orders = new ArrayList<>();
    private double revenue = 0;

    public void createOrder(String name, String email, String phone,
            String address, String city, String country,
            double price, int quantity, int customerType,
            boolean isPremium, boolean expressShipping,
            boolean giftWrap, boolean insurance) {

        if (name == null || name == "")
            return;
        if (email == null || email == "")
            return;
        if (price <= 0)
            return;

        Order order = new Order(name, email, phone, customerType, price, quantity, country, true);

        double discount = 0;
        if (customerType == 3 && price * quantity > 100) {
            discount = 0.1;
        } else if (customerType == 2 && price * quantity > 100) {
            discount = 0.08;
        }

        order.p = order.p * (1 - discount);

        if (expressShipping) {
            order.p = order.p + 9.99;
        }
        if (giftWrap) {
            order.p = order.p + 2.99;
        }
        if (insurance) {
            order.p = order.p + 4.99;
        }

        orders.add(order);

        revenue = revenue + (order.p * order.q);
    }

    public String formatPrice(double p, int tier, int qty) {
        double total = p * qty;

        if (tier == 3 && total > 100) {
            total = total * 0.9;
        } else if (tier == 2 && total > 100) {
            total = total * 0.92;
        }

        return "$" + String.format("%.2f", total);
    }

    public double getTotalWithTax(Order order) {
        double sub = order.p * order.q;

        if (order.cc.equals("ES")) {
            sub = sub * 1.21;
        } else if (order.cc.equals("FR")) {
            sub = sub * 1.20;
        } else if (order.cc.equals("DE")) {
            sub = sub * 1.19;
        } else {
            sub = sub * 1.15;
        }

        return sub;
    }

    public double calculateDiscount(Order order) {
        double total = order.p * order.q;
        double discount = 0;

        if (order.c == 3 && total > 100) {
            discount = total * 0.1;
        } else if (order.c == 2 && total > 100) {
            discount = total * 0.08;
        }

        return discount;
    }

    public void save(Order order, boolean email, boolean pdf, boolean backup) {
        if (order == null)
            return;
        if (!order.a)
            return;

        double total = getTotalWithTax(order);

        System.out.println("INSERT INTO orders VALUES ('" + order.n + "', " + total + ")");

        if (email) {
            System.out.println("EMAIL: Order confirmed for " + order.n);
            System.out.println("To: " + order.e);
            System.out.println("Total: $" + total);
        }

        if (pdf) {
            System.out.println("PDF: Generating invoice...");
            System.out.println("Customer: " + order.n);
            System.out.println("Amount: $" + total);
        }

        if (backup) {
            System.out.println("BACKUP: Saving to backup server...");
        }

        revenue = revenue + total;
    }

    public String generateReport(int type, boolean detailed, boolean includeInactive) {
        String report = "";

        if (type == 1) {
            if (detailed) {
                for (Order order : orders) {
                    if (order.a || includeInactive) {
                        report = report + "Order: " + order.n + " - $" + getTotalWithTax(order) + "\n";
                    }
                }
            } else {
                report = "Total Revenue: $" + revenue;
            }
        } else if (type == 2) {
            if (detailed) {
                for (Order order : orders) {
                    if (order.c == 3) {
                        report = report + "GOLD: " + order.n + "\n";
                    } else if (order.c == 2) {
                        report = report + "SILVER: " + order.n + "\n";
                    } else {
                        report = report + "NORMAL: " + order.n + "\n";
                    }
                }
            } else {
                int gold = 0, silver = 0, normal = 0;
                for (Order order : orders) {
                    if (order.c == 3)
                        gold++;
                    else if (order.c == 2)
                        silver++;
                    else
                        normal++;
                }
                report = "Gold: " + gold + ", Silver: " + silver + ", Normal: " + normal;
            }
        }

        return report;
    }

    public void stats(boolean print, boolean save, boolean email) {
        double avg = 0;
        double max = 0;
        double min = 999999;

        for (Order order : orders) {
            double t = order.p * order.q;
            avg = avg + t;
            if (t > max)
                max = t;
            if (t < min)
                min = t;
        }

        if (orders.size() > 0) {
            avg = avg / orders.size();
        }

        if (print) {
            System.out.println("=== STATISTICS ===");
            System.out.println("Average: $" + avg);
            System.out.println("Max: $" + max);
            System.out.println("Min: $" + min);
            System.out.println("Total Orders: " + orders.size());
        }

        if (save) {
            System.out.println("Saving statistics to file...");
        }

        if (email) {
            System.out.println("Emailing statistics to admin@company.com");
        }
    }

    public boolean validateAndProcess(Order order, int action) {
        if (order == null)
            return false;
        if (order.n == null || order.n == "")
            return false;
        if (order.e == null || order.e == "")
            return false;
        if (order.p <= 0)
            return false;
        if (order.q <= 0)
            return false;

        if (action == 1) {
            orders.add(order);
            revenue = revenue + order.p * order.q;
            System.out.println("Order processed normally");
            return true;
        } else if (action == 2) {
            orders.add(0, order);
            revenue = revenue + order.p * order.q * 1.5;
            System.out.println("URGENT order processed");
            return true;
        } else if (action == 3) {
            System.out.println("Order sent for review");
            if (order.p * order.q > 500) {
                System.out.println("High value - needs approval");
                return false;
            }
            orders.add(order);
            revenue = revenue + order.p * order.q;
            return true;
        }

        return false;
    }
}