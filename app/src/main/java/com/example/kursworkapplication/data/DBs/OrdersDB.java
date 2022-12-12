package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersDB {
    private DBHelper dbHelper;
    private LunchesDB lunchesDB;
    private CutleriesDB cutleriesDB;

    public OrdersDB(Context context){
        dbHelper = new DBHelper(context);
        lunchesDB = new LunchesDB(context);
        cutleriesDB = new CutleriesDB(context);
    }

    public Order get(Order order){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("orders", null, "id = ?",
                new String[] {String.valueOf(order.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("calorie");
            int wishIndex = c.getColumnIndex("wishes");
            int userLoginIndex = c.getColumnIndex("userLogin");
            Order ord = new Order();
            ord.setId(c.getInt(idIndex));
            ord.setCalorie(c.getInt(calIndex));
            ord.setWishes(c.getString(wishIndex));
            ord.setUserLogin(c.getString(userLoginIndex));
            if (ord.getUserLogin().equals(order.getUserLogin())){
                dbHelper.close();
                return ord;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Order order){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("calorie", order.getCalorie());
        cv.put("wishes", order.getWishes());
        cv.put("userLogin", order.getUserLogin());
        long orderId = db.insert("orders", null, cv);
        dbHelper.close();
    }

    public void update(Order order){
        if (get(order) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("calorie", order.getCalorie());
        cv.put("wishes", order.getWishes());
        cv.put("userLogin", order.getUserLogin());
        db.update("orders", cv, "id = ?", new String[] {String.valueOf(order.getId())});
        dbHelper.close();
    }

    public void delete(Order order){
        if (get(order) == null){
            return;
        }
        lunchesDB.delete(order);
        cutleriesDB.delete(order);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("orders", "id = " + order.getId(), null);
        dbHelper.close();
    }

    public List<Order> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Order> retList = new ArrayList<Order>();
        Cursor c = db.query("orders", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("calorie");
            int wishIndex = c.getColumnIndex("wishes");
            int userLoginIndex = c.getColumnIndex("userLogin");
            do{
                Order order = new Order();
                order.setId(c.getInt(idIndex));
                order.setCalorie(c.getInt(calIndex));
                order.setWishes(c.getString(wishIndex));
                order.setUserLogin(c.getString(userLoginIndex));
                retList.add(order);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Order> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Order> retList = new ArrayList<Order>();
        Cursor c = db.query("orders", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("calorie");
            int wishIndex = c.getColumnIndex("wishes");
            int userLoginIndex = c.getColumnIndex("userLogin");
            do{
                Order order = new Order();
                order.setId(c.getInt(idIndex));
                order.setCalorie(c.getInt(calIndex));
                order.setWishes(c.getString(wishIndex));
                order.setUserLogin(c.getString(userLoginIndex));
                retList.add(order);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "kursDBOrders", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table orders ("
                    + "id integer primary key autoincrement,"
                    + "calorie integer,"
                    + "wishes text,"
                    + "userLogin text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
