package com.example.pizzeriaapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;

/**
 * Activity class that specifies the attributes and actions for the Current
 * Order Activity.
 * This class also has a list view handler inner class with an item click
 * listener for the pizzas listview.
 *
 * @author Akshar Patel, Mervin James
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private ArrayAdapter<Pizza> pizzasAdapter;
    private TextView subtotal;
    private TextView salesTax;
    private TextView orderTotal;
    private StoreOrders ordersList;
    private Order order;

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
        setContentView(R.layout.activity_current_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView customerNumber =
                findViewById(R.id.customerPhoneNumberTextField);
        subtotal = findViewById(R.id.subtotalTextField);
        salesTax = findViewById(R.id.salesTaxTextField);
        orderTotal = findViewById(R.id.orderTotalTextField);
        ListView pizzas = findViewById(R.id.pizzasListView);
        Intent intent = getIntent();
        ordersList = (StoreOrders) intent.getSerializableExtra(
                "STORE_ORDERS");
        order = (Order) intent.getSerializableExtra("CURRENT_ORDER");
        customerNumber.setText(order.getPhoneNumber());
        pizzas.setOnItemClickListener(new PizzasListViewHandler());
        pizzasAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, order.getPizzas());
        pizzas.setAdapter(pizzasAdapter);
        pizzasAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateFields();
            }
        });
        updateFields();
    }

    /**
     * Handler method for the user when placing an order.
     *
     * @param view contains information about the current UI.
     */
    public void onPlaceOrderButtonClick(View view) {
        if (!order.getPizzas().isEmpty()) {
            ordersList.addOrder(order);
            Toast.makeText(getApplicationContext(), "Order placed!",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("STORE_ORDERS", this.ordersList);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "There are no pizzas" +
                            " in this order!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to update the textviews in the UI.
     */
    private void updateFields() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        subtotal.setText(df.format(order.subtotal()));
        salesTax.setText(df.format(order.salesTax()));
        orderTotal.setText(df.format(order.orderTotal()));
    }

    /**
     * Sets the back button in the toolbar to finish the current activity.
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
     * Inner class which attaches an item click listener to pizza's ListView.
     */
    class PizzasListViewHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            Pizza pizzaToRemove = (Pizza) parent.getItemAtPosition(position);
            pizzasAdapter.remove(pizzaToRemove);
            pizzasAdapter.notifyDataSetChanged();
            order.removePizza(pizzaToRemove);
        }
    }
}