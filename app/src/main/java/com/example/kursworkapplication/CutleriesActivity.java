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

import com.example.kursworkapplication.data.CutleriesData;
import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.LunchesData;

public class CutleriesActivity extends AppCompatActivity {
    String login = "";
    CutleriesData cutleriesData;
    ArrayAdapter<Cutlery> adapter;
    ListView listViewCutleries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutleries);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        cutleriesData = new CutleriesData(this, login);

        listViewCutleries = findViewById(R.id.cutleriesListView);
        Button add = findViewById(R.id.cutleriesButtonAdd);
        Button upd = findViewById(R.id.cutleriesButtonChange);
        Button del = findViewById(R.id.cutleriesButtonDelete);

        adapter = new ArrayAdapter<Cutlery>(this, R.layout.listitem,
                cutleriesData.findAllcutleries(login));
        listViewCutleries.setAdapter(adapter);
        listViewCutleries.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, CutleryActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int cutlery = -1;
            SparseBooleanArray sparseBooleanArray = listViewCutleries.getCheckedItemPositions();
            for (int i = 0; i < listViewCutleries.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    cutlery = adapter.getItem(i).getId();
                }
            }
            if (cutlery == -1){
                return;
            }
            Intent intent = new Intent(this, CutleryActivity.class);
            intent.putExtra("Id", cutlery);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewCutleries.clearChoices();
        });
        del.setOnClickListener(v -> {
            int cutlery = -1;
            SparseBooleanArray sparseBooleanArray = listViewCutleries.getCheckedItemPositions();
            for (int i = 0; i < listViewCutleries.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    cutlery = adapter.getItem(i).getId();
                }
            }
            if (cutlery != -1) {
                int finalCutlery = cutlery;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                cutleriesData.deletecutlery(finalCutlery, login);
                                listViewCutleries.clearChoices();
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