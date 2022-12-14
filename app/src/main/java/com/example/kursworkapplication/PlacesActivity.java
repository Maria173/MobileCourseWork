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

import com.example.kursworkapplication.data.PlacesData;
import com.example.kursworkapplication.data.Place;
import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.TripsData;

public class PlacesActivity extends AppCompatActivity {
    String login = "";
    PlacesData placesData;
    ArrayAdapter<Place> adapter;
    ListView listViewPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        placesData = new PlacesData(this, login);

        listViewPlaces = findViewById(R.id.placesListView);
        Button add = findViewById(R.id.placesButtonAdd);
        Button upd = findViewById(R.id.placesButtonChange);
        Button del = findViewById(R.id.placesButtonDelete);

        adapter = new ArrayAdapter<Place>(this, R.layout.listitem,
                placesData.findAllplaces(login));
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int place = -1;
            SparseBooleanArray sparseBooleanArray = listViewPlaces.getCheckedItemPositions();
            for (int i = 0; i < listViewPlaces.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    place = adapter.getItem(i).getId();
                }
            }
            if (place == -1){
                return;
            }
            Intent intent = new Intent(this, PlaceActivity.class);
            intent.putExtra("Id", place);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewPlaces.clearChoices();
        });
        del.setOnClickListener(v -> {
            int place = -1;
            SparseBooleanArray sparseBooleanArray = listViewPlaces.getCheckedItemPositions();
            for (int i = 0; i < listViewPlaces.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    place = adapter.getItem(i).getId();
                }
            }
            if (place != -1) {
                int finalPlace = place;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                placesData.deleteplace(finalPlace, login);
                                listViewPlaces.clearChoices();
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