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

import com.example.kursworkapplication.operator.Stop;
import com.example.kursworkapplication.operator.StopsData;

public class StopsActivity extends AppCompatActivity {

    String login = "";
    StopsData stopsData;
    ArrayAdapter<Stop> adapter;
    ListView listViewStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        stopsData = new StopsData(this, login);

        listViewStops = findViewById(R.id.stopsListView);
        Button add = findViewById(R.id.stopsButtonAdd);
        Button upd = findViewById(R.id.stopsButtonChange);
        Button del = findViewById(R.id.stopsButtonDelete);

        adapter = new ArrayAdapter<Stop>(this, android.R.layout.simple_list_item_single_choice,
                stopsData.findAllStops(login));
        listViewStops.setAdapter(adapter);
        listViewStops.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, StopActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int stop = -1;
            SparseBooleanArray sparseBooleanArray = listViewStops.getCheckedItemPositions();
            for (int i = 0; i < listViewStops.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    stop = adapter.getItem(i).getId();
                }
            }
            if (stop == -1){
                return;
            }
            Intent intent = new Intent(this, StopActivity.class);
            intent.putExtra("Id", stop);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewStops.clearChoices();
        });
        del.setOnClickListener(v -> {
            int stop = -1;
            SparseBooleanArray sparseBooleanArray = listViewStops.getCheckedItemPositions();
            for (int i = 0; i < listViewStops.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    stop = adapter.getItem(i).getId();
                }
            }
            if (stop != -1) {
                int finalStop = stop;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                stopsData.deleteStop(finalStop, login);
                                listViewStops.clearChoices();
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