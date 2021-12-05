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

        DecimalFormat df = new DecimalFormat("#,##0.00");
        pizzaPrice.setText(df.format(pizza.price()));
    }

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
            DecimalFormat df = new DecimalFormat("###,##0.00");
            pizzaPrice.setText(df.format(pizza.price()));
        }
        toppingToAdd = null;
    }

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
        DecimalFormat df = new DecimalFormat("###,##0.00");
        pizzaPrice.setText(df.format(pizza.price()));
        toppingToRemove = null;
    }

    public void onAddToOrderClick(View view) {
        currentOrder.addPizza(pizza);
        Toast.makeText(getApplicationContext(), "Pizza added to the order!"
                , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("CURRENT_ORDER", this.currentOrder);
        setResult(RESULT_OK, intent);
        finish();
    }

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
        DecimalFormat df = new DecimalFormat("#,##0.00");
        pizzaPrice.setText(String.valueOf(pizza.price()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //empty is fine
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ListViewHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            toppingToAdd = (Topping) parent.getItemAtPosition(position);
        }
    }

    class ListViewHandler2 implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            toppingToRemove = (Topping) parent.getItemAtPosition(position);
        }
    }
}