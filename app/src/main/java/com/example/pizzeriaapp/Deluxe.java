package com.example.pizzeriaapp;

import java.io.Serializable;

/**
 * A class that defines a Deluxe pizza with its default toppings. This
 * class contains an overridden price method that calculates the price of a
 * Deluxe pizza. There is also an overridden toString method to print the
 * type of pizza, the size, toppings, and price of the pizza.
 *
 * @author Mervin James, Akshar Patel
 */
public class Deluxe extends Pizza {
    private static final int MIN_TOPPINGS = 5;
    private static final double BASE_PRICE_DELUXE = 12.99;
    private static final double BASE_SIZE_COST = 0.0;

    /**
     * Constructor for a Deluxe pizza. Sets the default toppings and size
     * of the pizza.
     */
    public Deluxe() {
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.Sausage);
        toppings.add(Topping.Mushroom);
        toppings.add(Topping.Onion);
        toppings.add(Topping.GreenPepper);
        size = Size.small;
    }

    /**
     * Calculates the price of a Deluxe pizza based on the size selected
     * and the toppings added.
     *
     * @return price of a Deluxe pizza
     */
    @Override
    public double price() {
        double sizeCost = BASE_SIZE_COST;
        if (size == Size.medium) {
            sizeCost = ADDITIONAL_PRICE_MEDIUM_SIZE;
        } else if (size == Size.large) {
            sizeCost = ADDITIONAL_PRICE_LARGE_SIZE;
        }
        return (toppings.size() - MIN_TOPPINGS) * PRICE_PER_TOPPING +
                sizeCost + BASE_PRICE_DELUXE;
    }

    /**
     * Generates a String representation of a Deluxe pizza object.
     *
     * @return the String representation of a Deluxe pizza object.
     */
    @Override
    public String toString() {
        return "Deluxe" + super.toString();
    }


}
