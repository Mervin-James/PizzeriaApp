package com.example.pizzeriaapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;

public class CurrentOrderActivity extends AppCompatActivity {
    private ArrayAdapter<Pizza> pizzasAdapter;
    private TextView subtotal;
    private TextView salesTax;
    private TextView orderTotal;
    private StoreOrders ordersList;
    private Order order;

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

    private void updateFields() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        subtotal.setText(df.format(order.subtotal()));
        salesTax.setText(df.format(order.salesTax()));
        orderTotal.setText(df.format(order.orderTotal()));
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}