package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;

import java.util.ArrayList;
import java.util.List;

public class reportChoseOrdersActivity extends AppCompatActivity {

    String login = "";
    OrdersData ordersData;
    ArrayAdapter<Order> adapter;
    ListView listViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_chose_orders);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        ordersData = new OrdersData(this, login);

        listViewOrders = findViewById(R.id.reportOrdersListView);
        Button rep = findViewById(R.id.reportChoseOrders);

        adapter = new ArrayAdapter<Order>(this, R.layout.listitem,
                ordersData.findAllOrders(login));
        listViewOrders.setAdapter(adapter);
        listViewOrders.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter.notifyDataSetChanged();

        rep.setOnClickListener(v -> {
            List<Integer> orders = new ArrayList<Integer>();
            SparseBooleanArray sparseBooleanArray = listViewOrders.getCheckedItemPositions();
            for (int i = 0; i < listViewOrders.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    orders.add(adapter.getItem(i).getId());
                }
            }
            if (orders.size() == 0){
                Toast.makeText(this, "Хотя бы один заказ должен быть выбран",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int[] ord = orders.stream().mapToInt(i->i).toArray();
            Intent intent = new Intent(this, reportLunchesOrdersActivity.class);
            intent.putExtra("orders", ord);
            startActivity(intent);
            finish();
        });
    }
}