package com.example.pizzeriaapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that defines an Order object by a phone number. This class
 * contains methods to add and remove pizzas from an order, get the list of
 * pizzas in an order, get the phone number associated with an order,
 * calculate the subtotal of an order, calculate the sales tax of an order,
 * and calculate the order total.
 *
 * @author Mervin James, Akshar Patel
 */
public class Order implements Serializable {
    private static final double TAX_RATE = 0.06625;
    private final String phoneNumber;
    private final ArrayList<Pizza> pizzas = new ArrayList<Pizza>();

    /**
     * Constructs an Order object by a phone number.
     *
     * @param phoneNumber the phone number provided by the user.
     */
    public Order(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Adds a pizza to the order.
     *
     * @param pizza the pizza that will be added to the order.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the order.
     *
     * @param pizza the pizza that will be removed from the order.
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Getter method for the list of pizzas in an order.
     *
     * @return list of pizzas in an order.
     */
    public ArrayList<Pizza> getPizzas() {
        return this.pizzas;
    }

    /**
     * Getter method for the phone number associated with an order.
     *
     * @return phone number associated with an order.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Calculates the subtotal of an order.
     *
     * @return subtotal of an order.
     */
    public double subtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < pizzas.size(); i++) {
            subtotal += pizzas.get(i).price();
        }
        return subtotal;
    }

    /**
     * Calculates the sales tax of an order.
     *
     * @return sales tax of an order.
     */
    public double salesTax() {
        return this.subtotal() * TAX_RATE;
    }

    /**
     * Calculates the order total.
     *
     * @return order total.
     */
    public double orderTotal() {
        return this.subtotal() + this.salesTax();
    }
}
