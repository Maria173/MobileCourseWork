package com.example.kursworkapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

public class reportChoseExcursionsActivity extends AppCompatActivity {

    String login = "";
    ExcursionsData excursionsData;
    ArrayAdapter<Excursion> adapter;
    ListView listViewExcursions;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_chose_excursions);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        excursionsData = new ExcursionsData(this, login);

        listViewExcursions = findViewById(R.id.reportExcursionsListView);
        Button rep = findViewById(R.id.reportChoseExcursions);

        adapter = new ArrayAdapter<Excursion>(this, R.layout.listitem,
                excursionsData.findAllExcursions(login));
        listViewExcursions.setAdapter(adapter);
        listViewExcursions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter.notifyDataSetChanged();

        rep.setOnClickListener(v -> {
            List<Integer> excursions = new ArrayList<Integer>();
            SparseBooleanArray sparseBooleanArray = listViewExcursions.getCheckedItemPositions();
            for (int i = 0; i < listViewExcursions.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    excursions.add(adapter.getItem(i).getId());
                }
            }
            if (excursions.size() == 0){
                Toast.makeText(this, "Хотя бы один заказ должен быть выбран",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int[] ord = excursions.stream().mapToInt(i->i).toArray();
            Intent intent = new Intent(this, reportTripsExcursionsActivity.class);
            intent.putExtra("excursions", ord);
            startActivity(intent);
            finish();
        });
    }
}