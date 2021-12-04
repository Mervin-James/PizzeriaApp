package com.example.pizzeriaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private StoreOrders orders;
    private Order selectedOrder;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
//        Intent intent = getIntent();
//        orders = (StoreOrders) intent.getSerializableExtra("ORDER");
//        ArrayList<Order> ordersList = orders.getOrders();
//        ArrayList<String> stringOrdersList = new ArrayList<>();
//        for (Order order : ordersList) {
//            stringOrdersList.add(order.toString());
//        }
//        Spinner spinner = findViewById(R.id.phoneNumberSpinner);
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, stringOrdersList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
    }

    public void onCancelOrderClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}