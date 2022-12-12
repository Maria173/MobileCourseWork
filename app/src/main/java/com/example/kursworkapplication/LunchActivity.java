package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.LunchesData;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;

public class LunchActivity extends AppCompatActivity {
    String login = "";
    String role = "";
    int id = -1;
    LunchesData lunchesData;
    OrdersData ordersData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        lunchesData = new LunchesData(this, login);
        ordersData = new OrdersData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.lunchButtonSave);
        TextView price = findViewById(R.id.lunchEditTextPrice);
        TextView weight = findViewById(R.id.lunchEditTextWeight);
        Spinner spinner = findViewById(R.id.lunchSpinner);

        ArrayAdapter<Order> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ordersData.findAllOrders(login));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (id != -1){
            Lunch lunch = lunchesData.getLunch(id, login);
            if (lunch != null){
                price.setText(String.valueOf(lunch.getPrice()));
                weight.setText(String.valueOf(lunch.getWeight()));
                for (int i = 0; i < adapter.getCount(); ++i){
                    if(adapter.getItem(i).getId() == lunch.getOrder_id()){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        save.setOnClickListener(v -> {
            if (price.getText().toString().equals("") ||
                    !android.text.TextUtils.isDigitsOnly(price.getText().toString())){
                Toast.makeText(this, "цена должна быть не пустым числом",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (weight.getText().toString().equals("") ||
                    !android.text.TextUtils.isDigitsOnly(weight.getText().toString())){
                Toast.makeText(this, "вес должен быть не пустым числом",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int pr = Integer.parseInt(price.getText().toString());
            int we = Integer.parseInt(weight.getText().toString());
            if (id != -1){
                lunchesData.updateLunch(id, pr, we, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                lunchesData.addLunch(pr, we, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}