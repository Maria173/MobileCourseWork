package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class ReportsActivity extends AppCompatActivity {

    String login = "";
    String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");

        Button trips = findViewById(R.id.reportsButtonTrips);
        Button places = findViewById(R.id.reportsButtonPlaces);
        Button allUsers = findViewById(R.id.reportsButtonAllUsers);

        if (!Objects.equals(role, "admin")){
            allUsers.setVisibility(View.INVISIBLE);
        }

        allUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, reportUsersActivity.class);
            startActivity(intent);
        });
        places.setOnClickListener(v -> {
            Intent intent = new Intent(this, reportPlacesExcursionsActivity.class);
            startActivity(intent);
        });
        trips.setOnClickListener(v -> {
            Intent intent = new Intent(this, reportChoseExcursionsActivity.class);
            startActivity(intent);
        });
    }
}