package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;


public class ReportsMainActivity extends AppCompatActivity {

    String login = "";
    String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_main);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");

        Button tours = findViewById(R.id.reportsButtonTours);
        Button stops = findViewById(R.id.reportsButtonStops);
        Button allUsers = findViewById(R.id.reportsButtonAllUsers);

       if (!Objects.equals(role, "admin")){
          allUsers.setVisibility(View.INVISIBLE);
       }

        allUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportOperatorsActivity.class);
            startActivity(intent);
        });
        stops.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportStopsGuidesActivity.class);
            startActivity(intent);
        });
        tours.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportChoseGuidesActivity.class);
            startActivity(intent);
        });
    }
}