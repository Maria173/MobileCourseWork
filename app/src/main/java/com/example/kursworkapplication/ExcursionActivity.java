package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.ExcursionsData;

public class ExcursionActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    ExcursionsData excursionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        excursionsData = new ExcursionsData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.excursionButtonSave);
        TextView name = findViewById(R.id.excursionEditTextName);
        TextView type = findViewById(R.id.excursionEditTextType);

        if (id != -1){
            Excursion excursion = excursionsData.getExcursion(id, login);
            if (excursion != null){
                name.setText(excursion.getName());
                type.setText(excursion.getType());
            }
        }

        save.setOnClickListener(v -> {
            if (name.getText().toString().equals("")) {
                Toast.makeText(this, "Название не должно быть пустой строкой",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String cal = name.getText().toString();
            String wish = type.getText().toString();
            if (id != -1){
                excursionsData.updateExcursion(id, cal, wish, login);
            }
            else {
                excursionsData.addExcursion(cal, wish, login);
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}