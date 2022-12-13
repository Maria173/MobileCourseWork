package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.ExcursionsData;

import java.util.ArrayList;
import java.util.List;

public class ExcursionsActivity extends AppCompatActivity {

    String login = "";
    ExcursionsData excursionsData;
    ArrayAdapter<Excursion> adapter;
    ListView listViewExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursions);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        excursionsData = new ExcursionsData(this, login);

        listViewExcursions = findViewById(R.id.excursionsListView);
        Button add = findViewById(R.id.excursionsButtonAdd);
        Button upd = findViewById(R.id.excursionsButtonChange);
        Button del = findViewById(R.id.excursionsButtonDelete);

        adapter = new ArrayAdapter<Excursion>(this, R.layout.listitem,
                excursionsData.findAllExcursions(login));
        listViewExcursions.setAdapter(adapter);
        listViewExcursions.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExcursionActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int excursion = -1;
            SparseBooleanArray sparseBooleanArray = listViewExcursions.getCheckedItemPositions();
            for (int i = 0; i < listViewExcursions.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    excursion = adapter.getItem(i).getId();
                }
            }
            if (excursion == -1){
                return;
            }
            Intent intent = new Intent(this, ExcursionActivity.class);
            intent.putExtra("Id", excursion);
            //startActivity(intent);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewExcursions.clearChoices();
        });
        del.setOnClickListener(v -> {
            int excursion = -1;
            SparseBooleanArray sparseBooleanArray = listViewExcursions.getCheckedItemPositions();
            for (int i = 0; i < listViewExcursions.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    excursion = adapter.getItem(i).getId();
                }
            }
            if (excursion != -1) {
                int finalExcursion = excursion;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                excursionsData.deleteExcursion(finalExcursion, login);
                                listViewExcursions.clearChoices();
                                adapter.notifyDataSetChanged();
                            }})
                        .setNegativeButton("Нет", null).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}