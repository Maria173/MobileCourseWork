package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LunchesDB {
    private DBHelper dbHelper;

    public LunchesDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Lunch get(Lunch lunch){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("lunches", null, "id = ?",
                new String[] {String.valueOf(lunch.getId())},
        null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int weiIndex = c.getColumnIndex("weight");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            Lunch lun = new Lunch();
            lun.setId(c.getInt(idIndex));
            lun.setPrice(c.getInt(priIndex));
            lun.setWeight(c.getInt(weiIndex));
            lun.setUserLogin(c.getString(userLoginIndex));
            lun.setOrder_id(c.getInt(ordIdIndex));
            if (lun.getUserLogin().equals(lunch.getUserLogin())){
                dbHelper.close();
                return lun;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Lunch lunch){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("price", lunch.getPrice());
        cv.put("weight", lunch.getWeight());
        cv.put("userLogin", lunch.getUserLogin());
        cv.put("order_id", lunch.getOrder_id());
        long lunchId = db.insert("lunches", null, cv);
        dbHelper.close();
    }

    public void update(Lunch lunch){
        if (get(lunch) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("price", lunch.getPrice());
        cv.put("weight", lunch.getWeight());
        cv.put("userLogin", lunch.getUserLogin());
        cv.put("order_id", lunch.getOrder_id());
        db.update("lunches", cv, "id = ?", new String[] {String.valueOf(lunch.getId())});
        dbHelper.close();
    }

    public void delete(Lunch lunch){
        if(get(lunch) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("lunches", "id = " + lunch.getId(), null);
        dbHelper.close();
    }

    public void delete(Order order){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("lunches", "order_id = " + order.getId(), null);
        dbHelper.close();
    }

    public List<Lunch> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Lunch> retList = new ArrayList<Lunch>();
        Cursor c = db.query("lunches", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int weiIndex = c.getColumnIndex("weight");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            do{
                Lunch lun = new Lunch();
                lun.setId(c.getInt(idIndex));
                lun.setPrice(c.getInt(priIndex));
                lun.setWeight(c.getInt(weiIndex));
                lun.setUserLogin(c.getString(userLoginIndex));
                lun.setOrder_id(c.getInt(ordIdIndex));
                retList.add(lun);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Lunch> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Lunch> retList = new ArrayList<Lunch>();
        Cursor c = db.query("lunches", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int weiIndex = c.getColumnIndex("weight");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            do{
                Lunch lun = new Lunch();
                lun.setId(c.getInt(idIndex));
                lun.setPrice(c.getInt(priIndex));
                lun.setWeight(c.getInt(weiIndex));
                lun.setUserLogin(c.getString(userLoginIndex));
                lun.setOrder_id(c.getInt(ordIdIndex));
                retList.add(lun);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "kursDBLunches", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table lunches ("
                    + "id integer primary key autoincrement,"
                    + "price integer,"
                    + "weight integer,"
                    + "userLogin text,"
                    + "order_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
