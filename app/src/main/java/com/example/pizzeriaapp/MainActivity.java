package com.example.pizzeriaapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private static final int PHONE_NUMBER_DIGITS = 10;
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
//        if(savedInstanceState != null) {
//            StoreOrders restoredOrders =
//                    (StoreOrders) savedInstanceState.getSerializable(
//                            "RESTORED_ORDERS");
//            Order restoredSelectedOrder =
//                    (Order) savedInstanceState.getSerializable(
//                            "RESTORED_SELECTED_ORDER");
//            if (restoredSelectedOrder != null)
//                phoneNumberTf.setText(restoredSelectedOrder.getPhoneNumber());
//            this.orders = restoredOrders != null ? restoredOrders :
//                    new StoreOrders();
//            this.selectedOrder = restoredSelectedOrder;
//        } else {
//            this.orders = new StoreOrders();
//        }
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
        startActivity(intent);
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
        startActivity(intent);
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
        startActivity(intent);
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
        startActivity(intent);
    }

    public void onStoreOrdersClick(View view) {
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        intent.putExtra("SELECTED_ORDER", this.selectedOrder);
        intent.putExtra("STORE_ORDERS", this.orders);
        startActivity(intent);
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

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("RESTORED_ORDERS", orders);
//        outState.putSerializable("RESTORED_SELECTED_ORDER", selectedOrder);
//        outState.putString("yes", "hi");
//    }
}