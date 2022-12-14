package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainOperatorActivity extends AppCompatActivity {

    String login = "";
    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_operator);


        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");


        Button tours = findViewById(R.id.mainButtonTour);
        Button guides = findViewById(R.id.mainButtonGuide);
        Button stops = findViewById(R.id.mainButtonStop);
        Button reports = findViewById(R.id.mainButtonReports);

        tours.setOnClickListener(v -> {
            Intent intent = new Intent(this, ToursActivity.class);
            startActivity(intent);
        });
        guides.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuidesActivity.class);
            startActivity(intent);
        });
        stops.setOnClickListener(v -> {
            Intent intent = new Intent(this, StopsActivity.class);
            startActivity(intent);
        });
        reports.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportsMainActivity.class);
            startActivity(intent);
        });
    }
}