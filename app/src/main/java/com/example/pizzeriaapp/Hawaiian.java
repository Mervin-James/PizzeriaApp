package com.example.pizzeriaapp;

/**
 * A class that defines a Hawaiian pizza with its default toppings. This
 * class contains an overridden price method that calculates the price of a
 * Hawaiian pizza. There is also an overridden toString method to print the
 * type of pizza, the size, toppings, and price of the pizza.
 *
 * @author Mervin James, Akshar Patel
 */
public class Hawaiian extends Pizza {
    private static final int MIN_TOPPINGS = 2;
    private static final double BASE_PRICE_HAWAIIAN = 10.99;
    private static final double BASE_SIZE_COST = 0.0;

    /**
     * Constructor for a Hawaiian pizza. Sets the default toppings and size
     * of the pizza.
     */
    public Hawaiian() {
        toppings.add(Topping.Pineapple);
        toppings.add(Topping.Ham);
        size = Size.small;
    }

    /**
     * Calculates the price of a Hawaiian pizza based on the size selected
     * and the toppings added.
     *
     * @return price of a Hawaiian pizza
     */
    @Override public double price() {
        double sizeCost = BASE_SIZE_COST;
        if (size == Size.medium) {
            sizeCost = ADDITIONAL_PRICE_MEDIUM_SIZE;
        } else if (size == Size.large) {
            sizeCost = ADDITIONAL_PRICE_LARGE_SIZE;
        }
        return (toppings.size() - MIN_TOPPINGS) * PRICE_PER_TOPPING +
                sizeCost + BASE_PRICE_HAWAIIAN;
    }

    /**
     * Generates a String representation of a Hawaiian pizza object.
     *
     * @return the String representation of a Hawaiian pizza object.
     */
    @Override
    public String toString() {
        return "Hawaiian" + super.toString();
    }
}
