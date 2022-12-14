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

import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.TripsData;
import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.ExcursionsData;

public class TripActivity extends AppCompatActivity {
    String login = "";
    String role = "";
    int id = -1;
    TripsData tripsData;
    ExcursionsData excursionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        tripsData = new TripsData(this, login);
        excursionsData = new ExcursionsData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.tripButtonSave);
        TextView name = findViewById(R.id.tripEditTextName);
        TextView days = findViewById(R.id.tripEditTextDays);
        Spinner spinner = findViewById(R.id.tripSpinner);

        ArrayAdapter<Excursion> adapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, excursionsData.findAllExcursions(login));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        if (id != -1){
            Trip trip = tripsData.getTrip(id, login);
            if (trip != null){
                name.setText(trip.getName());
                days.setText(String.valueOf(trip.getDays()));
                for (int i = 0; i < adapter1.getCount(); ++i){
                    if(adapter1.getItem(i).getId() == trip.getExcursion_id()){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        save.setOnClickListener(v -> {
            if (name.getText().toString().equals("")){
                Toast.makeText(this, "название должно быть не пустой строкой",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (days.getText().toString().equals("")){
                Toast.makeText(this, "количество дней должно быть не пустым числом",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String pr = name.getText().toString();
            int we = Integer.parseInt(days.getText().toString());
            if (id != -1){
                tripsData.updateTrip(id, pr, we, login,
                        adapter1.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                tripsData.addTrip(pr, we, login,
                        adapter1.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}