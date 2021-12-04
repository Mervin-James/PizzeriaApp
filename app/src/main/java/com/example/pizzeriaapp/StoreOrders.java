package com.example.pizzeriaapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A class that defines a StoreOrders object by a list of current orders.
 * This class contains methods to get the list of orders, add and remove
 * orders, and export the orders to a text file.
 *
 * @author Mervin James, Akshar Patel
 */
public class StoreOrders implements Serializable {
    private final ArrayList<Order> orders;

    /**
     * Constructs a StoreOrders object and creates a new list of orders.
     */
    public StoreOrders() {
        this.orders = new ArrayList<Order>();
    }

    /**
     * Getter method for the list of orders.
     *
     * @return list of orders.
     */
    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    /**
     * Adds an order to the list of orders.
     *
     * @param order order that will be added to the list of orders.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an order from the list of orders.
     *
     * @param order order that will be removed from the list of orders.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Exports the orders to a text file.
     *
     * @param targetFile file that the orders will be written to.
     * @throws FileNotFoundException exception caused when a file is not
     * found.
     */
    public void export(File targetFile) throws FileNotFoundException {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        PrintWriter pw = new PrintWriter(targetFile);
        for (Order order : orders) {
            pw.println("Customer phone number: " + order.getPhoneNumber());
            pw.println("\torders:");
            for (int j = 0; j < order.getPizzas().size(); j++) {
                Pizza pizza = order.getPizzas().get(j);
                pw.println("\t\t" + pizza.toString());
            }
            pw.println("\tsubtotal: $" + df.format(order.subtotal()));
            pw.println("\tsales tax: $" + df.format(order.salesTax()));
            pw.println("\torder total: $" + df.format(order.orderTotal()));
        }
        pw.close();
    }
}
