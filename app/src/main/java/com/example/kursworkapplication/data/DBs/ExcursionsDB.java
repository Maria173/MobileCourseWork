package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcursionsDB {
    private DBHelper dbHelper;
    private TripsDB tripsDB;
    private PlacesDB placesDB;

    public ExcursionsDB(Context context){
        dbHelper = new DBHelper(context);
        tripsDB = new TripsDB(context);
        placesDB = new PlacesDB(context);
    }

    public Excursion get(Excursion excursion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("excursions", null, "id = ?",
                new String[] {String.valueOf(excursion.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("name");
            int wishIndex = c.getColumnIndex("type");
            int userLoginIndex = c.getColumnIndex("userLogin");
            Excursion ord = new Excursion();
            ord.setId(c.getInt(idIndex));
            ord.setName(c.getString(calIndex));
            ord.setType(c.getString(wishIndex));
            ord.setUserLogin(c.getString(userLoginIndex));
            if (ord.getUserLogin().equals(excursion.getUserLogin())){
                dbHelper.close();
                return ord;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Excursion excursion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", excursion.getName());
        cv.put("type", excursion.getType());
        cv.put("userLogin", excursion.getUserLogin());
        long excursionId = db.insert("excursions", null, cv);
        dbHelper.close();
    }

    public void update(Excursion excursion){
        if (get(excursion) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", excursion.getName());
        cv.put("type", excursion.getType());
        cv.put("userLogin", excursion.getUserLogin());
        db.update("excursions", cv, "id = ?", new String[] {String.valueOf(excursion.getId())});
        dbHelper.close();
    }

    public void delete(Excursion excursion){
        if (get(excursion) == null){
            return;
        }
        tripsDB.delete(excursion);
        placesDB.delete(excursion);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("excursions", "id = " + excursion.getId(), null);
        dbHelper.close();
    }

    public List<Excursion> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Excursion> retList = new ArrayList<Excursion>();
        Cursor c = db.query("excursions", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("name");
            int wishIndex = c.getColumnIndex("type");
            int userLoginIndex = c.getColumnIndex("userLogin");
            do{
                Excursion excursion = new Excursion();
                excursion.setId(c.getInt(idIndex));
                excursion.setName(c.getString(calIndex));
                excursion.setType(c.getString(wishIndex));
                excursion.setUserLogin(c.getString(userLoginIndex));
                retList.add(excursion);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Excursion> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Excursion> retList = new ArrayList<Excursion>();
        Cursor c = db.query("excursions", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int calIndex = c.getColumnIndex("name");
            int wishIndex = c.getColumnIndex("type");
            int userLoginIndex = c.getColumnIndex("userLogin");
            do{
                Excursion excursion = new Excursion();
                excursion.setId(c.getInt(idIndex));
                excursion.setName(c.getString(calIndex));
                excursion.setType(c.getString(wishIndex));
                excursion.setUserLogin(c.getString(userLoginIndex));
                retList.add(excursion);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "kursDBExcursions1", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table excursions ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "type text,"
                    + "userLogin text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
