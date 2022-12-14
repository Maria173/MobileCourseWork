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
import com.example.kursworkapplication.operator.Stop;
import com.example.kursworkapplication.operator.StopsData;

public class StopActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    StopsData stopsData;
    GuidesData guidesData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        stopsData = new StopsData(this, login);
        guidesData = new GuidesData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.stopButtonSave);
        TextView price = findViewById(R.id.stopEditTextPrice);
        TextView name = findViewById(R.id.stopEditTextName);
        Spinner spinner = findViewById(R.id.stopSpinner);

        ArrayAdapter<Guide> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, guidesData.findAllGuides(login));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (id != -1){
            Stop stop = stopsData.getStop(id, login);
            if (stop != null){
                price.setText(String.valueOf(stop.getPrice()));
                name.setText(stop.getNameStop());
                for (int i = 0; i < adapter.getCount(); ++i){
                    if(adapter.getItem(i).getId() == stop.getGuide_id()){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }

        save.setOnClickListener(v -> {
            if (price.getText().toString().equals("")){
                Toast.makeText(this, "Цену необходимо заполнить",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (name.getText().toString().equals("")){
                Toast.makeText(this, "Название необходимо заполнить",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int pr = Integer.parseInt(price.getText().toString());
            String na = name.getText().toString();
            if (id != -1){
                stopsData.updateStop(id, pr, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                stopsData.addStop(pr, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}