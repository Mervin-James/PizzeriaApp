package com.example.pizzeriaapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private static final int PHONE_NUMBER_DIGITS = 10;
    private TextInputLayout customerNumberLayout;
    private TextView phoneNumberTf;
    private ImageButton deluxeBtn, hawaiianBtn, pepperoniBtn,
            currentOrdersBtn, storeOrdersBtn;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerNumberLayout =
                findViewById(R.id.customerNumberTextInputLayout);
        phoneNumberTf = findViewById(R.id.customerNumberTextField);
        deluxeBtn = findViewById(R.id.deluxeButton);
        hawaiianBtn = findViewById(R.id.hawaiianButton);
        pepperoniBtn = findViewById(R.id.pepperoniButton);
        currentOrdersBtn = findViewById(R.id.currentOrdersButton);
        storeOrdersBtn = findViewById(R.id.storeOrdersButton);
    }

    public void onDeluxeClick(View view) {
        if(isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Deluxe"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.deluxe_pizza);
        startActivity(intent);
    }

    public void onHawaiianClick(View view) {
        if(isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Hawaiian"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.hawaiian_pizza);
        startActivity(intent);
    }

    public void onPepperoniClick(View view) {
        if(isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Pepperoni"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.pepperoni_pizza);
        startActivity(intent);
    }

    public void onCurrentOrdersClick(View view) {
        if(isPhoneNumberInvalid()) return;

    }

    public void onStoreOrdersClick(View view) {

    }

    private boolean isPhoneNumberInvalid() {
        String phoneNumber = phoneNumberTf.getText().toString();
        if(phoneNumber.length() != PHONE_NUMBER_DIGITS || !phoneNumber.matches("[0-9]+")) {
            customerNumberLayout.setError("Please enter a 10 digit phone " +
                    "number");
            return true;
        }
        customerNumberLayout.setError(null);
        return false;
    }



}