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

import com.example.kursworkapplication.data.CutleriesData;
import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.LunchesData;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;

public class CutleryActivity extends AppCompatActivity {
    String login = "";
    String role = "";
    int id = -1;
    CutleriesData cutleriesData;
    OrdersData ordersData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutlery);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        cutleriesData = new CutleriesData(this, login);
        ordersData = new OrdersData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.cutleryButtonSave);
        TextView count = findViewById(R.id.cutleryEditTextCount);
        TextView name = findViewById(R.id.cutleryEditTextName);
        Spinner spinner = findViewById(R.id.cutlerySpinner);

        ArrayAdapter<Order> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ordersData.findAllOrders(login));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (id != -1){
            Cutlery cutlery = cutleriesData.getcutlery(id, login);
            if (cutlery != null){
                count.setText(String.valueOf(cutlery.getCount()));
                name.setText(cutlery.getName());
                for (int i = 0; i < adapter.getCount(); ++i){
                    if(adapter.getItem(i).getId() == cutlery.getOrder_id()){
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
                cutleriesData.updatecutlery(id, co, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            else {
                cutleriesData.addcutlery(co, na, login,
                        adapter.getItem((int) spinner.getSelectedItemId()).getId());
            }
            //finish();
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}