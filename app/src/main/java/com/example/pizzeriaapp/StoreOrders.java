package com.example.pizzeriaapp;

import java.io.Serializable;
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

}
