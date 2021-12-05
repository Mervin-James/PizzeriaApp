package com.example.pizzeriaapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private StoreOrders orders;
    private Order selectedOrder;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<Pizza> orderAdapter;
    ArrayList<Order> ordersList;
    ArrayList<String> stringOrdersList;
    private ListView ordersListView;
    Spinner spinner;
    TextView storeOrderTotal;
    Button cancelOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Intent intent = getIntent();
        orders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        ordersList = orders.getOrders();
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        cancelOrderButton.setEnabled(false);
        if (!ordersList.isEmpty()) {
            stringOrdersList= new ArrayList<>();
            for (Order order : ordersList) {
                stringOrdersList.add(order.getPhoneNumber());
            }
            selectedOrder = ordersList.get(0);

            spinner = findViewById(R.id.phoneNumberSpinner);
            adapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, stringOrdersList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            storeOrderTotal = findViewById(R.id.storeOrderTotalTextField);
            ordersListView = findViewById(R.id.storeOrdersListView);
            orderAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, selectedOrder.getPizzas());
            ordersListView.setAdapter(orderAdapter);
            cancelOrderButton.setEnabled(true);
            updateOrderTotal();
        }
    }

    private void updateOrderTotal() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        storeOrderTotal.setText(df.format(selectedOrder.orderTotal()));
    }

    private void selectedCustomerPhoneNumber(String phoneNumber) {
        selectedOrder = null;
        if (phoneNumber != null) {
            Order foundOrder = null;
            for (Order order : orders.getOrders()) {
                if (phoneNumber.equals(order.getPhoneNumber())) {
                    foundOrder = order;
                    break;
                }
            }
            selectedOrder = foundOrder;
        }
    }

    public void onCancelOrderClick(View view) {
        orders.removeOrder(selectedOrder);
        adapter.clear();
        stringOrdersList.clear();
        for (Order order : ordersList) {
            stringOrdersList.add(order.getPhoneNumber());
        }
        spinner.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, stringOrdersList));
        if (ordersList.isEmpty()) {
            ordersListView.setAdapter(null);
        }
        updateOrderTotal();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String phoneNumber = (String) parent.getSelectedItem();
        selectedCustomerPhoneNumber(phoneNumber);
        ordersListView.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                selectedOrder.getPizzas()));
        updateOrderTotal();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("STORE_ORDERS", this.orders);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("STORE_ORDERS", this.orders);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}