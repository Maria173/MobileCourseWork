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

import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.GuidesData;

public class GuidesActivity extends AppCompatActivity {
    String login = "";
    GuidesData guidesData;
    ArrayAdapter<Guide> adapter;
    ListView listViewGuides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        guidesData = new GuidesData(this, login);

        listViewGuides = findViewById(R.id.guidesListView);
        Button add = findViewById(R.id.guidesButtonAdd);
        Button upd = findViewById(R.id.guidesButtonChange);
        Button del = findViewById(R.id.guidesButtonDelete);

        adapter = new ArrayAdapter<Guide>(this, android.R.layout.simple_list_item_single_choice,
                guidesData.findAllGuides(login));
        listViewGuides.setAdapter(adapter);
        listViewGuides.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuideActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int guide = -1;
            SparseBooleanArray sparseBooleanArray = listViewGuides.getCheckedItemPositions();
            for (int i = 0; i < listViewGuides.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    guide = adapter.getItem(i).getId();
                }
            }
            if (guide == -1){
                return;
            }
            Intent intent = new Intent(this, GuideActivity.class);
            intent.putExtra("Id", guide);
            //startActivity(intent);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewGuides.clearChoices();
        });
        del.setOnClickListener(v -> {
            int guide = -1;
            SparseBooleanArray sparseBooleanArray = listViewGuides.getCheckedItemPositions();
            for (int i = 0; i < listViewGuides.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    guide = adapter.getItem(i).getId();
                }
            }
            if (guide != -1) {
                int finalGuide = guide;
                new AlertDialog.Builder(this)
                        .setTitle("Удаление")
                        .setMessage("Вы уверены что хотите удалить запись?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                guidesData.deleteGuide(finalGuide, login);
                                listViewGuides.clearChoices();
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