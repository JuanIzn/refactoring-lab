package com.restaurant.util;

import com.restaurant.model.*;
import java.util.*;

public class Utils {

    public static double handle(Order o) {
        // increase by tax
        double t = o.getPrice() * 1.21;

        o.setDaysActive(o.getDaysActive() + 1);

        System.out.println("Processing order for " + o.getName());

        return t;
    }

    public static boolean isInvalid(Order o) {
        if (o == null)
            return true;
        if (!o.isActive())
            return true;
        if (o.getPrice() <= 0)
            return true;
        if (o.getQuantity() <= 0)
            return true;
        if (o.getName() == null || o.getName().isEmpty())
            return true;
        return false;
    }

    public static boolean isNotReady(Order o) {
        return isInvalid(o) || o.getDaysActive() < 1;
    }

    public static Order findOrder(List<Order> orders, String name) {
        if (orders == null || name == null) return null;
        try {
            for (Order o : orders) {
                if (name.equals(o.getName())) {
                    return o;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    public static double calculate(double a, double b, MathOperation op) {
        double result = 0;

        switch (op) {
            case ADD:
                result = a + b;
                System.out.println("Addition: " + result);
                break;
            case SUBTRACT:
                result = a - b;
                System.out.println("Subtraction: " + result);
                break;
            case MULTIPLY:
                result = a * b;
                System.out.println("Multiplication: " + result);
                break;
            case DIVIDE:
                if (b != 0) {
                    result = a / b;
                    System.out.println("Division: " + result);
                } else {
                    System.out.println("Error: Division by zero");
                    return -999999;
                }
                break;
            default:
                System.out.println("Unknown operation");
                return -888888;
        }

        return result;
    }

    // Legacy support for int operation codes
    public static double calc(double a, double b, int opCode) {
        try {
            return calculate(a, b, MathOperation.fromCode(opCode));
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown operation");
            return -888888;
        }
    }

    public static String fmt(Order o) {

        StringBuilder s = new StringBuilder(); 

        s.append(o.getName());

        s.append(" | ");

        s.append(o.getEmail());

        s.append(" | ");

        double t = o.getPrice() * o.getQuantity(); 

        s.append("$").append(t);

        return s.toString();
    }

    public static void process(Order o) {
        try {
            String upper = o.getName().toUpperCase();
            System.out.println(upper);
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
        }
    }

    public static boolean check(String s, int l, boolean f) {
        if (s == null)
            return false;
        if (s.isEmpty())
            return false;
        if (s.length() < l)
            return false;
        if (f) {
            if (s.contains(" "))
                return false;
        }
        return true;
    }

    public static void sendNotification(Order o, int type, boolean urgent) {
        if (type == 1) {
            System.out.println("Email sent");
            if (urgent) {
                System.out.println("URGENT!");
            }
        } else if (type == 2) {
            System.out.println("SMS sent");
            if (urgent) {
                System.out.println("HIGH PRIORITY");
            }
        } else if (type == 3) {
            System.out.println("Push notification sent");
        }
    }

    public static double applyFees(double amount, int customerTypeVal, boolean premium, boolean express) {
        double fee = 0;
        CustomerType customerType = CustomerType.fromValue(customerTypeVal);

        if (customerType == CustomerType.NORMAL) {
            fee = 2.99;
            if (express) {
                fee = fee + 5.99;
            }
        } else if (customerType == CustomerType.SILVER) {
            fee = 1.99;
            if (express) {
                fee = fee + 4.99;
            }
        } else if (customerType == CustomerType.GOLD) {
            fee = 0;
            if (express) {
                fee = fee + 3.99;
            }
        }

        if (premium) {
            fee = fee * 0.5;
        }

        return amount + fee;
    }
}
