package com.example.pizzeriaapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TextView phoneNumberTf;
    private ImageButton deluxeBtn, hawaiianBtn, pepperoniBtn,
            currentOrdersBtn, storeOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberTf = findViewById(R.id.customerNumberTextField);
        deluxeBtn = findViewById(R.id.deluxeButton);
        hawaiianBtn = findViewById(R.id.hawaiianButton);
        pepperoniBtn = findViewById(R.id.pepperoniButton);
        currentOrdersBtn = findViewById(R.id.currentOrdersButton);
        storeOrdersBtn = findViewById(R.id.storeOrdersButton);
    }

    public void onDeluxeClick(View view) {
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Deluxe"));
        intent.putExtra("PIZZA_IMG", R.drawable.deluxe_pizza);
        startActivity(intent);
    }

    public void onHawaiianClick(View view) {

    }

    public void onPepperoniClick(View view) {

    }

    public void onCurrentOrdersClick(View view) {

    }

    public void onStoreOrdersClick(View view) {

    }



}