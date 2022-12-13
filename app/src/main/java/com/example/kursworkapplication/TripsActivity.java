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

import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.TripsData;

public class TripsActivity extends AppCompatActivity {
    String login = "";
    TripsData tripsData;
    ArrayAdapter<Trip> adapter;
    ListView listViewTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        tripsData = new TripsData(this, login);

        listViewTrips = findViewById(R.id.tripsListView);
        Button add = findViewById(R.id.tripsButtonAdd);
        Button upd = findViewById(R.id.tripsButtonChange);
        Button del = findViewById(R.id.tripsButtonDelete);

        adapter = new ArrayAdapter<Trip>(this, R.layout.listitem,
                tripsData.findAllTrips(login));
        listViewTrips.setAdapter(adapter);
        listViewTrips.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, TripActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int trip = -1;
            SparseBooleanArray sparseBooleanArray = listViewTrips.getCheckedItemPositions();
            for (int i = 0; i < listViewTrips.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    trip = adapter.getItem(i).getId();
                }
            }
            if (trip == -1){
                return;
            }
            Intent intent = new Intent(this, TripActivity.class);
            intent.putExtra("Id", trip);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewTrips.clearChoices();
        });
        del.setOnClickListener(v -> {
            int trip = -1;
            SparseBooleanArray sparseBooleanArray = listViewTrips.getCheckedItemPositions();
            for (int i = 0; i < listViewTrips.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    trip = adapter.getItem(i).getId();
                }
            }
            if (trip != -1) {
                int finalTrip = trip;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tripsData.deleteTrip(finalTrip, login);
                                listViewTrips.clearChoices();
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