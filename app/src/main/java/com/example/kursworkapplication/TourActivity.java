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

import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.GuidesData;
import com.example.kursworkapplication.operator.Tour;
import com.example.kursworkapplication.operator.ToursData;

public class TourActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    ToursData toursData;
    GuidesData guidesData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        toursData = new ToursData(this, login);
        guidesData = new GuidesData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.tourButtonSave);
        TextView name = findViewById(R.id.tourEditName);
        Spinner spinner = findViewById(R.id.tourSpinner);

        ArrayAdapter<Guide> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, guidesData.findAllGuides(login));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (id != -1){
            Tour tour = toursData.getTour(id, login);
            if (tour != null){
                name.setText(tour.getName());
                for (int i = 0; i < adapter.getCount(); ++i){
                    if(adapter.getItem(i).getId() == tour.getGuide_id()){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        save.setOnClickListener(v -> {
            if (name.getText().equals("")){
                Toast.makeText(this, "нужно заполнить название тура",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String nm = name.getText().toString();
            if (id != -1){
                toursData.updateTour(id, nm, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                toursData.addTour(nm, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}