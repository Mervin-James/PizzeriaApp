package com.example.pizzeriaapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class PizzaCustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private ListView list1, list2;
    private ImageView pizzaView;
    private ArrayAdapter adapter1, adapter2, adapter3;
    private String[] toppings = {"Sausage","Chicken", "Beef", "Ham",
            "Pineapple", "BlackOlives", "GreenPepper", "Onion", "Pepperoni",
            "Mushroom"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_customization_activity);
        Intent intent = getIntent();
        Pizza pizza = (Pizza) intent.getSerializableExtra("SELECTED_PIZZA");
        int pizzaImg = intent.getIntExtra("PIZZA_IMG", 0);
        pizzaView = findViewById(R.id.pizzaView);
        pizzaView.setImageResource(pizzaImg);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter1 =
                ArrayAdapter.createFromResource(this,
                R.array.sizes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

//        adapter2 = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, toppings);
//        list1 = findViewById(R.id.additionalToppings);
//        list1.setAdapter(adapter2);
//        list1.setOnItemClickListener(new ListViewHandler());
//        list2 = findViewById(R.id.selectedToppings);
//        adapter3 = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, defaultToppings);
//        list2.setAdapter(adapter3);
//        list2.setOnItemClickListener(new ListViewHandler2());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String topping = (String) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //empty is fine
    }

    /**
     * Inner class to implement the OnItemClickListener Interface
     * Any selection of the data items in the ListView will navigate back to the MainActivity
     */
//    class ListViewHandler implements AdapterView.OnItemClickListener{
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            displayExtra.setText(list.getAdapter().getItem(position).toString());
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }
//    }

    /**
     * Inner class to implement the OnItemClickListener Interface
     * On selection of the data item in the ListView, display the item selected with a short Toast message
     */
    class ListViewHandler2 implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), list2.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
        }
    }


}