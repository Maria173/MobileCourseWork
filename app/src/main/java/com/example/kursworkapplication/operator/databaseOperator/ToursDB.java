package com.example.kursworkapplication.operator.databaseOperator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.DBs.LunchesDB;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.Operator;
import com.example.kursworkapplication.operator.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToursDB {
    private ToursDB.DBHelper dbHelper;

    public ToursDB(Context context){
        dbHelper = new ToursDB.DBHelper(context);
    }

    public Tour get(Tour tour){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("tours", null, "id = ?",
                new String[] {String.valueOf(tour.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            Tour tr = new Tour();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setOperatorLogin(c.getString(operatorLoginIndex));
            tr.setGuide_id(c.getInt(guiIdIndex));
            if (tr.getOperatorLogin().equals(tour.getOperatorLogin())){
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Tour tour){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", tour.getName());
        cv.put("operatorLogin", tour.getOperatorLogin());
        cv.put("guide_id", tour.getGuide_id());
        long tourId = db.insert("tours", null, cv);
        dbHelper.close();
    }

    public void update(Tour tour){
        if (get(tour) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", tour.getName());
        cv.put("operatorLogin", tour.getOperatorLogin());
        cv.put("guide_id", tour.getGuide_id());
        db.update("tours", cv, "id = ?", new String[] {String.valueOf(tour.getId())});
        dbHelper.close();
    }

    public void delete(Tour tour){
        if(get(tour) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tours", "id = " + tour.getId(), null);
        dbHelper.close();
    }

    public void delete(Guide guide){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tours", "guide_id = " + guide.getId(), null);
        dbHelper.close();
    }

    public List<Tour> readAll(Operator operator){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Tour> retList = new ArrayList<Tour>();
        Cursor c = db.query("tours", null, "operatorLogin = ?",
                new String[] {operator.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            do{
                Tour tr = new Tour();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setOperatorLogin(c.getString(operatorLoginIndex));
                tr.setGuide_id(c.getInt(guiIdIndex));
                retList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Tour> readAllOperators(Operator operator){
        if (!Objects.equals(operator.getRole(), "admin")){
            return readAll(operator);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Tour> retList = new ArrayList<Tour>();
        Cursor c = db.query("tours", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            do{
                Tour tr = new Tour();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setOperatorLogin(c.getString(operatorLoginIndex));
                tr.setGuide_id(c.getInt(guiIdIndex));
                retList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseTourDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table tours ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "operatorLogin text,"
                    + "guide_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
