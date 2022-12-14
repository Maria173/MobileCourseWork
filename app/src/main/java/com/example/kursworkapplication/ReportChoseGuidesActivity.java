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

import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;
import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.GuidesData;

import java.util.ArrayList;
import java.util.List;


public class ReportChoseGuidesActivity extends AppCompatActivity {

    String login = "";
    GuidesData guidesData;
    ArrayAdapter<Guide> adapter;
    ListView listViewGuides;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_chose_guides);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        guidesData = new GuidesData(this, login);

        listViewGuides = findViewById(R.id.reportGuidesListView);
        Button rep = findViewById(R.id.reportChoseGuides);

        adapter = new ArrayAdapter<Guide>(this, android.R.layout.simple_list_item_single_choice,
                guidesData.findAllGuides(login));
        listViewGuides.setAdapter(adapter);
        listViewGuides.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter.notifyDataSetChanged();

        rep.setOnClickListener(v -> {
            List<Integer> orders = new ArrayList<Integer>();
            SparseBooleanArray sparseBooleanArray = listViewGuides.getCheckedItemPositions();
            for (int i = 0; i < listViewGuides.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    orders.add(adapter.getItem(i).getId());
                }
            }
            if (orders.size() == 0){
                Toast.makeText(this, "Выберите хотя бы одного гида",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int[] ord = orders.stream().mapToInt(i->i).toArray();
            Intent intent = new Intent(this, ReportToursGuidesActivity.class);
            intent.putExtra("guides", ord);
            startActivity(intent);
            finish();
        });
    }
}