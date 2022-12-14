package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button tourist = findViewById(R.id.startButtonTourist);
        Button operator = findViewById(R.id.startButtonOperator);
        tourist.setOnClickListener(v -> {
            Intent intent = new Intent(this, AutorizationActivity.class);
            startActivity(intent);
        });
        operator.setOnClickListener(v -> {
            Intent intent = new Intent(this, AutorizationOperatorActivity.class);
            startActivity(intent);
        });
    }
}