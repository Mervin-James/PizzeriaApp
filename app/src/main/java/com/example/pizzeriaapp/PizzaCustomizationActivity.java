package com.example.pizzeriaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Activity class that specifies the attributes and actions for the Pizza
 * Customization Activity.
 *
 * @author Akshar Patel, Mervin James
 */
public class PizzaCustomizationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    Topping toppingToAdd;
    Topping toppingToRemove;
    private TextView pizzaPrice;
    private ArrayAdapter additionalToppingsAdapter, selectedToppingsAdapter;
    private ArrayList<Topping> toppings;
    private ArrayList<Topping> defaultToppings;
    private Pizza pizza;
    private Order currentOrder;

    /**
     * Creates this activity when instantiated.
     *
     * @param savedInstanceState a bundle that contains any saved
     *                           information about this activity's prior
     *                           state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_customization);
        setupPizza();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        additionalToppingsAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, toppings);
        ListView additionalToppingsList =
                findViewById(R.id.additionalToppings);
        additionalToppingsList.setAdapter(additionalToppingsAdapter);
        additionalToppingsList.setOnItemClickListener(new ListViewHandler());

        ArrayList<Topping> initialToppings = new ArrayList<>();
        initialToppings.addAll(defaultToppings);

        selectedToppingsAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, initialToppings);
        ListView selectedToppingsList = findViewById(R.id.selectedToppings);
        selectedToppingsList.setAdapter(selectedToppingsAdapter);
        selectedToppingsList.setOnItemClickListener(new ListViewHandler2());

        updatePrice();
    }

    /**
     * Retrieves pizza and current order from MainActivity and adds the
     * default toppings to the pizza.
     */
    private void setupPizza() {
        Intent intent = getIntent();
        pizza = (Pizza) intent.getSerializableExtra("SELECTED_PIZZA");
        pizza.setSize(Size.small);
        currentOrder = (Order) intent.getSerializableExtra("SELECTED_ORDER");
        int pizzaImg = intent.getIntExtra("PIZZA_IMG", 0);
        ImageView pizzaView = findViewById(R.id.pizzaView);
        pizzaView.setImageResource(pizzaImg);
        pizzaPrice = findViewById(R.id.pizzaPrice);

        toppings = new ArrayList<>();
        toppings.add(Topping.Sausage);
        toppings.add(Topping.Chicken);
        toppings.add(Topping.Beef);
        toppings.add(Topping.Ham);
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.GreenPepper);
        toppings.add(Topping.Onion);
        toppings.add(Topping.Mushroom);
        toppings.add(Topping.Pineapple);
        toppings.add(Topping.BlackOlives);

        defaultToppings = new ArrayList<>();
        defaultToppings.addAll(pizza.getToppings());
        toppings.removeAll(defaultToppings);
    }

    /**
     * Adds the selected topping to the pizza.
     *
     * @param view contains information about the current UI.
     */
    public void onAddButtonClick(View view) {
        int position = additionalToppingsAdapter.getPosition(toppingToAdd);
        if (toppingToAdd != null) {
            boolean isToppingAdded = pizza.addTopping(toppingToAdd);
            if (isToppingAdded) {
                additionalToppingsAdapter.remove(additionalToppingsAdapter
                        .getItem(position));
                selectedToppingsAdapter.add(toppingToAdd);
                additionalToppingsAdapter.notifyDataSetChanged();
                selectedToppingsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Maximum number of toppings\n" +
                                "At most 7 toppings!",
                        Toast.LENGTH_SHORT).show();
            }
            updatePrice();
        }
        toppingToAdd = null;
    }

    /**
     * Removes the selected topping from the pizza.
     *
     * @param view contains information about the current UI.
     */
    public void onRemoveButtonClick(View view) {
        int position = selectedToppingsAdapter.getPosition(toppingToRemove);
        boolean isDefaultTopping = defaultToppings.contains(toppingToRemove);
        if (toppingToRemove != null && !isDefaultTopping) {
            selectedToppingsAdapter.remove(selectedToppingsAdapter
                    .getItem(position));
            additionalToppingsAdapter.add(toppingToRemove);
            additionalToppingsAdapter.notifyDataSetChanged();
            selectedToppingsAdapter.notifyDataSetChanged();
            pizza.removeTopping(toppingToRemove);
        } else if (isDefaultTopping) {
            Toast.makeText(getApplicationContext(), "You are removing " +
                    "the essential toppings!", Toast.LENGTH_SHORT).show();
        }
        updatePrice();
        toppingToRemove = null;
    }

    /**
     * Adds pizza to current order.
     *
     * @param view contains information about the current UI.
     */
    public void onAddToOrderClick(View view) {
        currentOrder.addPizza(pizza);
        Toast.makeText(getApplicationContext(), "Pizza added to the order!"
                , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("CURRENT_ORDER", this.currentOrder);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Updates the price of the pizza.
     */
    private void updatePrice() {
        DecimalFormat df = new DecimalFormat("###,##0.00");
        pizzaPrice.setText(df.format(pizza.price()));
    }

    /**
     * Updates price when a size is selected from spinner.
     *
     * @param parent the AdapterView where the selection happened
     * @param view the view within the AdapterView that was clicked
     * @param position the position of the view in the adapter
     * @param id the row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String size = (String) parent.getSelectedItem();
        if (Objects.equals(size, "small")) {
            pizza.setSize(Size.small);
        } else if (Objects.equals(size, "medium")) {
            pizza.setSize(Size.medium);
        } else if (Objects.equals(size, "large")) {
            pizza.setSize(Size.large);
        }
        updatePrice();
    }

    /**
     * Callback method that is invoked when the selection disappears from
     * this view.
     *
     * @param parent the AdapterView that now contains no selected item
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //empty is fine
    }

    /**
     * Sets the back button in the view's toolbar to finish the current
     * activity.
     *
     * @param item the MenuItem being selected.
     * @return true if the selected button is the back button on the
     * toolbar, else returns the super method's result.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Inner class which attaches an item click listener to
     * additionalToppings ListView.
     */
    class ListViewHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            toppingToAdd = (Topping) parent.getItemAtPosition(position);
        }
    }

    /**
     * Inner class which attaches an item click listener to
     * selectedToppings ListView.
     */
    class ListViewHandler2 implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            toppingToRemove = (Topping) parent.getItemAtPosition(position);
        }
    }
}