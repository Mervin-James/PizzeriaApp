package com.example.pizzeriaapp;

/**
 * A class that defines a Pepperoni pizza with its default toppings. This
 * class contains an overridden price method that calculates the price of a
 * Pepperoni pizza. There is also an overridden toString method to print the
 * type of pizza, the size, toppings, and price of the pizza.
 *
 * @author Mervin James, Akshar Patel
 */
public class Pepperoni extends Pizza {
    private static final int MIN_TOPPINGS = 1;
    private static final double BASE_PRICE_PEPPERONI = 8.99;
    private static final double BASE_SIZE_COST = 0.0;

    /**
     * Constructor for a Pepperoni pizza. Sets the default toppings and size
     * of the pizza.
     */
    public Pepperoni() {
        toppings.add(Topping.Pepperoni);
        size = Size.small;
    }

    /**
     * Calculates the price of a Pepperoni pizza based on the size selected
     * and the toppings added.
     *
     * @return price of a Pepperoni pizza
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
                sizeCost + BASE_PRICE_PEPPERONI;
    }

    /**
     * Generates a String representation of a Pepperoni pizza object.
     *
     * @return the String representation of a Pepperoni pizza object.
     */
    @Override
    public String toString() {
        return "Pepperoni" + super.toString();
    }
}
