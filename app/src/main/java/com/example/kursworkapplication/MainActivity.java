package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String login = "";
    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");


        Button lunches = findViewById(R.id.mainButtonLunch);
        Button orders = findViewById(R.id.mainButtonOrders);
        Button cutleries = findViewById(R.id.mainButtonCutlery);
        Button reports = findViewById(R.id.mainButtonReports);

        lunches.setOnClickListener(v -> {
            Intent intent = new Intent(this, LunchesActivity.class);
            startActivity(intent);
        });
        orders.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
        });
        cutleries.setOnClickListener(v -> {
            Intent intent = new Intent(this, CutleriesActivity.class);
            startActivity(intent);
        });
        reports.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportsActivity.class);
            startActivity(intent);
        });
    }
}