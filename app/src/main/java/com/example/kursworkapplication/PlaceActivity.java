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

import com.example.kursworkapplication.data.PlacesData;
import com.example.kursworkapplication.data.Place;
import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.TripsData;
import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.ExcursionsData;

public class PlaceActivity extends AppCompatActivity {
    String login = "";
    String role = "";
    int id = -1;
    PlacesData placesData;
    ExcursionsData excursionsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        placesData = new PlacesData(this, login);
        excursionsData = new ExcursionsData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.placeButtonSave);
        TextView count = findViewById(R.id.placeEditTextCount);
        TextView name = findViewById(R.id.placeEditTextName);
        Spinner spinner = findViewById(R.id.placeSpinner);

        ArrayAdapter<Excursion> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, excursionsData.findAllExcursions(login));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (id != -1){
            Place place = placesData.getplace(id, login);
            if (place != null){
                count.setText(String.valueOf(place.getCount()));
                name.setText(place.getName());
                for (int i = 0; i < adapter.getCount(); ++i){
                    if(adapter.getItem(i).getId() == place.getExcursion_id()){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        save.setOnClickListener(v -> {
            if (count.getText().toString().equals("") ||
                    !android.text.TextUtils.isDigitsOnly(count.getText().toString())){
                Toast.makeText(this, "Количество должно быть не пустым числом",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (name.getText().toString().equals("")){
                Toast.makeText(this, "Название не должно быть пустым",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int co = Integer.parseInt(count.getText().toString());
            String na = name.getText().toString();
            if (id != -1){
                placesData.updateplace(id, co, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                placesData.addplace(co, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}