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

import com.example.kursworkapplication.operator.Tour;
import com.example.kursworkapplication.operator.ToursData;

public class ToursActivity extends AppCompatActivity {

    String login = "";
    ToursData toursData;
    ArrayAdapter<Tour> adapter;
    ListView listViewTours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        toursData = new ToursData(this, login);

        listViewTours = findViewById(R.id.toursListView);
        Button add = findViewById(R.id.toursButtonAdd);
        Button upd = findViewById(R.id.toursButtonChange);
        Button del = findViewById(R.id.toursButtonDelete);

        adapter = new ArrayAdapter<Tour>(this, android.R.layout.simple_list_item_single_choice,
                toursData.findAllTours(login));
        listViewTours.setAdapter(adapter);
        listViewTours.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, TourActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int tour = -1;
            SparseBooleanArray sparseBooleanArray = listViewTours.getCheckedItemPositions();
            for (int i = 0; i < listViewTours.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    tour = adapter.getItem(i).getId();
                }
            }
            if (tour == -1){
                return;
            }
            Intent intent = new Intent(this, TourActivity.class);
            intent.putExtra("Id", tour);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewTours.clearChoices();
        });
        del.setOnClickListener(v -> {
            int tour = -1;
            SparseBooleanArray sparseBooleanArray = listViewTours.getCheckedItemPositions();
            for (int i = 0; i < listViewTours.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    tour = adapter.getItem(i).getId();
                }
            }
            if (tour != -1) {
                int finalTour = tour;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                toursData.deleteTour(finalTour, login);
                                listViewTours.clearChoices();
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