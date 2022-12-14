package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TripsDB {
    private DBHelper dbHelper;

    public TripsDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Trip get(Trip trip){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("trips", null, "id = ?",
                new String[] {String.valueOf(trip.getId())},
        null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("name");
            int weiIndex = c.getColumnIndex("days");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            Trip lun = new Trip();
            lun.setId(c.getInt(idIndex));
            lun.setName(c.getString(priIndex));
            lun.setDays(c.getInt(weiIndex));
            lun.setUserLogin(c.getString(userLoginIndex));
            lun.setExcursion_id(c.getInt(ordIdIndex));
            if (lun.getUserLogin().equals(trip.getUserLogin())){
                dbHelper.close();
                return lun;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Trip trip){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", trip.getName());
        cv.put("days", trip.getDays());
        cv.put("userLogin", trip.getUserLogin());
        cv.put("excursion_id", trip.getExcursion_id());
        long tripId = db.insert("trips", null, cv);
        dbHelper.close();
    }

    public void update(Trip trip){
        if (get(trip) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", trip.getName());
        cv.put("days", trip.getDays());
        cv.put("userLogin", trip.getUserLogin());
        cv.put("excursion_id", trip.getExcursion_id());
        db.update("trips", cv, "id = ?", new String[] {String.valueOf(trip.getId())});
        dbHelper.close();
    }

    public void delete(Trip trip){
        if(get(trip) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("trips", "id = " + trip.getId(), null);
        dbHelper.close();
    }

    public void delete(Excursion excursion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("trips", "excursion_id = " + excursion.getId(), null);
        dbHelper.close();
    }

    public List<Trip> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Trip> retList = new ArrayList<Trip>();
        Cursor c = db.query("trips", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("name");
            int weiIndex = c.getColumnIndex("days");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            do{
                Trip lun = new Trip();
                lun.setId(c.getInt(idIndex));
                lun.setName(c.getString(priIndex));
                lun.setDays(c.getInt(weiIndex));
                lun.setUserLogin(c.getString(userLoginIndex));
                lun.setExcursion_id(c.getInt(ordIdIndex));
                retList.add(lun);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Trip> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Trip> retList = new ArrayList<Trip>();
        Cursor c = db.query("trips", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("name");
            int weiIndex = c.getColumnIndex("days");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            do{
                Trip lun = new Trip();
                lun.setId(c.getInt(idIndex));
                lun.setName(c.getString(priIndex));
                lun.setDays(c.getInt(weiIndex));
                lun.setUserLogin(c.getString(userLoginIndex));
                lun.setExcursion_id(c.getInt(ordIdIndex));
                retList.add(lun);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "kursDBTrips", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table trips ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "days integer,"
                    + "userLogin text,"
                    + "excursion_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
