package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.GuidesData;

public class GuideActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    GuidesData guidesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        guidesData = new GuidesData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.guideButtonSave);
        TextView name = findViewById(R.id.guideEditTextName);
        TextView salary = findViewById(R.id.guideEditTextSalary);

        if (id != -1){
            Guide guide = guidesData.getGuide(id, login);
            if (guide != null){
                name.setText(guide.getNameGuide());
                salary.setText(String.valueOf(guide.getSalary()));
            }
        }

        save.setOnClickListener(v -> {
            if (salary.getText().toString().equals("")){
                Toast.makeText(this, "Зарплату нужно заполнить",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int sal = Integer.parseInt(salary.getText().toString());
            String nm = name.getText().toString();
            if (id != -1){
                guidesData.updateGuide(id, nm, sal, login);
            }
            else {
                guidesData.addGuide(nm, sal, login);
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}