package com.example.pizzeriaapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private static final int PHONE_NUMBER_DIGITS = 10;
    private static final int PIZZA_CUSTOMIZATION_REQUEST_CODE = 1;
    private static final int CURRENT_ORDER_REQUEST_CODE = 2;
    private static final int STORE_ORDERS_REQUEST_CODE = 3;
    private TextInputLayout customerNumberLayout;
    private TextView phoneNumberTf;
    private StoreOrders orders;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerNumberLayout =
                findViewById(R.id.customerNumberTextInputLayout);
        phoneNumberTf = findViewById(R.id.customerNumberTextField);
        this.orders = new StoreOrders();
    }

    public void onDeluxeClick(View view) {
        if (isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Deluxe"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText().toString())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.deluxe_pizza);
        startActivityForResult(intent, PIZZA_CUSTOMIZATION_REQUEST_CODE);
    }

    public void onHawaiianClick(View view) {
        if (isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA", PizzaMaker.createPizza("Hawaiian"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText().toString())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.hawaiian_pizza);
        startActivityForResult(intent, PIZZA_CUSTOMIZATION_REQUEST_CODE);
    }

    public void onPepperoniClick(View view) {
        if (isPhoneNumberInvalid()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        intent.putExtra("SELECTED_PIZZA",
                PizzaMaker.createPizza("Pepperoni"));
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText().toString())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("PIZZA_IMG", R.drawable.pepperoni_pizza);
        startActivityForResult(intent, PIZZA_CUSTOMIZATION_REQUEST_CODE);
    }

    public void onCurrentOrdersClick(View view) {
        if (isPhoneNumberInvalid()) return;
        if (selectedOrder == null || !selectedOrder.getPhoneNumber()
                .equals(phoneNumberTf.getText().toString())) {
            selectedOrder = new Order(phoneNumberTf.getText().toString());
        }
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("STORE_ORDERS", this.orders);
        intent.putExtra("CURRENT_ORDER", this.selectedOrder);
        startActivityForResult(intent, CURRENT_ORDER_REQUEST_CODE);
    }

    public void onStoreOrdersClick(View view) {

    }

    private boolean isPhoneNumberInvalid() {
        String phoneNumber = phoneNumberTf.getText().toString();
        if (phoneNumber.length() != PHONE_NUMBER_DIGITS ||
                !phoneNumber.matches("[0-9]+")) {
            customerNumberLayout.setError("Please enter a 10 digit phone " +
                    "number");
            return true;
        }
        customerNumberLayout.setError(null);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;
        if (requestCode == PIZZA_CUSTOMIZATION_REQUEST_CODE) {
            this.selectedOrder = (Order) intent.getSerializableExtra(
                    "CURRENT_ORDER");
        } else if (requestCode == CURRENT_ORDER_REQUEST_CODE) {
            this.orders = (StoreOrders) intent.getSerializableExtra(
                    "STORE_ORDERS");
            this.selectedOrder = null;
            phoneNumberTf.setText("");
        } else if (requestCode == STORE_ORDERS_REQUEST_CODE) {

        }
    }

}