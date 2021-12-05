package com.example.pizzeriaapp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Activity class that specifies the attributes and actions for the Main
 * Activity (home screen of the app).
 *
 * @author Akshar Patel, Mervin James
 */
public class MainActivity extends AppCompatActivity {
    private static final int PHONE_NUMBER_DIGITS = 10;
    private static final int PIZZA_CUSTOMIZATION_REQUEST_CODE = 1;
    private static final int CURRENT_ORDER_REQUEST_CODE = 2;
    private static final int STORE_ORDERS_REQUEST_CODE = 3;
    private TextInputLayout customerNumberLayout;
    private TextView phoneNumberTf;
    private StoreOrders orders;
    private Order selectedOrder;

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
        setContentView(R.layout.activity_main);
        customerNumberLayout =
                findViewById(R.id.customerNumberTextInputLayout);
        phoneNumberTf = findViewById(R.id.customerNumberTextField);
        this.orders = new StoreOrders();
    }

    /**
     * Handles users trying to add a deluxe pizza to the order.
     *
     * @param view contains information about the current UI.
     */
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

    /**
     * Handles users trying to add a hawaiian pizza to the order.
     *
     * @param view contains information about the current UI.
     */
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

    /**
     * Handles users trying to add a pepperoni pizza to the order.
     *
     * @param view contains information about the current UI.
     */
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

    /**
     * Handles users trying to get the overview of the current order.
     *
     * @param view contains information about the current UI.
     */
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

    /**
     * Handles users trying to get the overview of all the store orders.
     *
     * @param view contains information about the current UI.
     */
    public void onStoreOrdersClick(View view) {
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        intent.putExtra("STORE_ORDERS", this.orders);
        startActivityForResult(intent, STORE_ORDERS_REQUEST_CODE);
    }

    /**
     * Helper method that validates the inputted customer phone number.
     * This method also sends a feedback message if there is an error.
     *
     * @return true if the customer's phone number is invalid, false
     * otherwise.
     */
    private boolean isPhoneNumberInvalid() {
        String phoneNumber = phoneNumberTf.getText().toString();
        if (phoneNumber.length() != PHONE_NUMBER_DIGITS ||
                !phoneNumber.matches("[0-9]+")) {
            customerNumberLayout.setError("Please enter a 10 digit phone " +
                    "number");
            Toast.makeText(getApplicationContext(), "Please enter a 10 " +
                            "digit phone number",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        customerNumberLayout.setError(null);
        return false;
    }

    /**
     * Handles incoming intents when child activities are finished.
     * @param requestCode identifies the child activity.
     * @param resultCode the status of the task of the child activity.
     * @param intent contains information about the child activity's results.
     */
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
            this.orders = (StoreOrders) intent.getSerializableExtra(
                    "STORE_ORDERS");
        }
    }

}