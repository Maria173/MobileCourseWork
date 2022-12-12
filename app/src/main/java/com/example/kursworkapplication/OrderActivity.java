package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;

public class OrderActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    OrdersData ordersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        ordersData = new OrdersData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.orderButtonSave);
        TextView calorie = findViewById(R.id.orderEditTextCalorie);
        TextView wishes = findViewById(R.id.orderEditTextWishes);

        if (id != -1){
            Order order = ordersData.getOrder(id, login);
            if (order != null){
                calorie.setText(String.valueOf(order.getCalorie()));
                wishes.setText(order.getWishes());
            }
        }

        save.setOnClickListener(v -> {
            if (calorie.getText().toString().equals("") ||
                    !android.text.TextUtils.isDigitsOnly(calorie.getText().toString())){
                Toast.makeText(this, "Калории не должны быть пустым числом",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int cal = Integer.parseInt(calorie.getText().toString());
            String wish = wishes.getText().toString();
            if (id != -1){
                ordersData.updateOrder(id, cal, wish, login);
            }
            else {
                ordersData.addOrder(cal, wish, login);
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}