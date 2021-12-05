package com.example.pizzeriaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity class that specifies the attributes and actions for the Store
 * Orders Activity.
 *
 * @author Akshar Patel, Mervin James
 */
public class StoreOrdersActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private StoreOrders orders;
    private Order selectedOrder;
    private ArrayAdapter<String> adapter;
    private ArrayList<Order> ordersList;
    private ArrayList<String> stringOrdersList;
    private ListView ordersListView;
    private Spinner spinner;
    private TextView storeOrderTotal;
    private Button cancelOrderButton;

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
        setContentView(R.layout.activity_store_orders);
        Intent intent = getIntent();
        orders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        ordersList = orders.getOrders();
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        cancelOrderButton.setEnabled(false);
        if (!ordersList.isEmpty()) {
            stringOrdersList = new ArrayList<>();
            for (Order order : ordersList) {
                stringOrdersList.add(order.getPhoneNumber());
            }
            selectedOrder = ordersList.get(0);

            spinner = findViewById(R.id.phoneNumberSpinner);
            adapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, stringOrdersList);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            storeOrderTotal = findViewById(R.id.storeOrderTotalTextField);
            ordersListView = findViewById(R.id.storeOrdersListView);
            ArrayAdapter<Pizza> orderAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1,
                    selectedOrder.getPizzas());
            ordersListView.setAdapter(orderAdapter);
            cancelOrderButton.setEnabled(true);
            updateOrderTotal();
        }
    }

    /**
     * Updates the order total for the selected order.
     */
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

    /**
     * Cancels an order when the Cancel Order button is clicked.
     *
     * @param view contains information about the current UI.
     */
    public void onCancelOrderClick(View view) {
        orders.removeOrder(selectedOrder);
        adapter.clear();
        stringOrdersList.clear();
        for (Order order : ordersList) {
            stringOrdersList.add(order.getPhoneNumber());
        }
        spinner.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, stringOrdersList));
        if (selectedOrder != null) {
            updateOrderTotal();
        }
        if (ordersList.isEmpty()) {
            ordersListView.setAdapter(null);
            storeOrderTotal.setText(getResources()
                    .getString(R.string.defaultPrice));
            cancelOrderButton.setEnabled(false);
        }
    }

    /**
     * Updates list views when a phone number is selected from spinner.
     *
     * @param parent   the AdapterView where the selection happened
     * @param view     the view within the AdapterView that was clicked
     * @param position the position of the view in the adapter
     * @param id       the row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String phoneNumber = (String) parent.getSelectedItem();
        selectedCustomerPhoneNumber(phoneNumber);
        ordersListView.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                selectedOrder.getPizzas()));
        updateOrderTotal();
    }

    /**
     * Callback method that is invoked when the selection disappears from
     * this view.
     *
     * @param parent the AdapterView that now contains no selected item
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // empty is fine
    }

    /**
     * Sets the back button in the view's toolbar to finish the current
     * activity.
     *
     * @param item the MenuItem being selected.
     * @return true if the selected button is the back button on the
     * toolbar, else returns the super method's result.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sends store orders to MainActivity when back button is pressed.
     */
    @Override
    public void onBackPressed() {
        handleBack();
        super.onBackPressed();
    }

    /**
     * Helper method that creates the intent to be sent to Main Activity.
     */
    private void handleBack() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("STORE_ORDERS", this.orders);
        setResult(RESULT_OK, intent);
        finish();
    }
}