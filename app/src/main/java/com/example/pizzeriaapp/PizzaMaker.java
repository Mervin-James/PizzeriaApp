package com.example.pizzeriaapp;

/**
 * A class that creates instances of different types of pizzas.
 *
 * @author Mervin James, Akshar Patel
 */
public class PizzaMaker {
    /**
     * Creates a new instance of the selected type of pizza.
     *
     * @param flavor the flavor of the selected pizza.
     * @return new Pizza object.
     */
    public static Pizza createPizza(String flavor) {
        switch (flavor) {
            case "Hawaiian":
                return new Hawaiian();
            case "Deluxe":
                return new Deluxe();
            default:
                return new Pepperoni();
        }
    }
}
