package com.example.pizzeriaapp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Abstract class that defines a Pizza. This class contains a template
 * method called price() which is overridden in all the subclasses. This
 * class also has a method to add toppings, remove toppings, get and set
 * the size of a pizza, get the toppings of a pizza, and a toString method
 * to print the type of pizza, the size, toppings, and price of the pizza.
 *
 * @author Mervin James, Akshar Patel
 */
public abstract class Pizza implements Serializable {
    protected static final double ADDITIONAL_PRICE_MEDIUM_SIZE = 2.00;
    protected static final double ADDITIONAL_PRICE_LARGE_SIZE = 4.00;
    protected static final int MAX_TOPPINGS = 7;
    protected static final double PRICE_PER_TOPPING = 1.49;
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Abstract method that is overridden in the subclasses to calculate
     * the price of a pizza.
     *
     * @return price of the pizza.
     */
    public abstract double price();

    /**
     * Adds a topping to a pizza.
     *
     * @param topping the pizza topping to be added.
     * @return true if the topping is able to be added, otherwise false.
     */
    public boolean addTopping(Topping topping) {
        if (toppings.size() < MAX_TOPPINGS) {
            return toppings.add(topping);
        }
        return false;
    }

    /**
     * Removes a topping from a pizza.
     *
     * @param topping the pizza topping to be added.
     * @return true if the topping is able to be removed, otherwise false.
     */
    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    /**
     * Setter method for the size of the pizza.
     *
     * @param size size of the pizza.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Getter method for the list of toppings on a pizza.
     *
     * @return list of toppings on a pizza.
     */
    public ArrayList<Topping> getToppings() {
        return this.toppings;
    }

    /**
     * Generates a String representation of a Pizza object.
     *
     * @return the String representation of a Pizza object.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        //removes leading and trailing brackets
        String toppingsListed = toppings.toString()
                .substring(1, toppings.toString().length() - 1);
        return " pizza, " + toppingsListed + ", " + size + ", " + "$" +
                df.format(price());
    }

}
