package com.example.pizzeriaapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private StoreOrders orders;
    private Order selectedOrder;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<Order> orderAdapter;
    ArrayList<Order> ordersList;
    ArrayList<String> stringOrdersList;
    private ListView ordersListView;
    Spinner spinner;
    TextView storeOrderTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        Intent intent = getIntent();
        selectedOrder = (Order) intent.getSerializableExtra("SELECTED_ORDER");
        orders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        ordersList = orders.getOrders();
        stringOrdersList= new ArrayList<>();
        for (Order order : ordersList) {
            stringOrdersList.add(order.getPhoneNumber());
        }
        spinner = findViewById(R.id.phoneNumberSpinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stringOrdersList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        storeOrderTotal = findViewById(R.id.storeOrderTotalTextField);
        ordersListView = findViewById(R.id.storeOrdersListView);
        orderAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, selectedOrder.getPizzas());
        ordersListView.setAdapter(orderAdapter);
        updateOrderTotal();

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
        for (Order order : ordersList) {
            stringOrdersList.add(order.getPhoneNumber());
        }
        spinner.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, stringOrdersList));
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
}