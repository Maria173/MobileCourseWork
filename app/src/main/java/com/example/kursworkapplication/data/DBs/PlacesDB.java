package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.Place;
import com.example.kursworkapplication.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlacesDB {
    private DBHelper dbHelper;

    public PlacesDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Place get(Place place){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("places", null, "id = ?",
                new String[] {String.valueOf(place.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            Place cut = new Place();
            cut.setId(c.getInt(idIndex));
            cut.setCount(c.getInt(priIndex));
            cut.setName(c.getString(weiIndex));
            cut.setUserLogin(c.getString(userLoginIndex));
            cut.setExcursion_id(c.getInt(ordIdIndex));
            if (cut.getUserLogin().equals(place.getUserLogin())){
                dbHelper.close();
                return cut;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Place place){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("count", place.getCount());
        cv.put("name", place.getName());
        cv.put("userLogin", place.getUserLogin());
        cv.put("excursion_id", place.getExcursion_id());
        long placeId = db.insert("places", null, cv);
        dbHelper.close();
    }

    public void update(Place place){
        if (get(place) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("count", place.getCount());
        cv.put("name", place.getName());
        cv.put("userLogin", place.getUserLogin());
        cv.put("excursion_id", place.getExcursion_id());
        db.update("places", cv, "id = ?", new String[] {String.valueOf(place.getId())});
        dbHelper.close();
    }

    public void delete(Place place){
        if(get(place) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("places", "id = " + place.getId(), null);
        dbHelper.close();
    }

    public void delete(Excursion excursion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("places", "excursion_id = " + excursion.getId(), null);
        dbHelper.close();
    }

    public List<Place> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Place> retList = new ArrayList<Place>();
        Cursor c = db.query("places", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            do{
                Place cut = new Place();
                cut.setId(c.getInt(idIndex));
                cut.setCount(c.getInt(priIndex));
                cut.setName(c.getString(weiIndex));
                cut.setUserLogin(c.getString(userLoginIndex));
                cut.setExcursion_id(c.getInt(ordIdIndex));
                retList.add(cut);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Place> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Place> retList = new ArrayList<Place>();
        Cursor c = db.query("places", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("excursion_id");
            do{
                Place lun = new Place();
                lun.setId(c.getInt(idIndex));
                lun.setCount(c.getInt(priIndex));
                lun.setName(c.getString(weiIndex));
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
            super(context, "kursDBPlaces", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table places ("
                    + "id integer primary key autoincrement,"
                    + "count integer,"
                    + "name text,"
                    + "userLogin text,"
                    + "excursion_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
