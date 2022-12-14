package com.example.kursworkapplication.operator.databaseOperator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuidesDB {
    private GuidesDB.DBHelper dbHelper;
    private ToursDB toursDB;
    private StopsDB stopsDB;

    public GuidesDB(Context context){
        dbHelper = new GuidesDB.DBHelper(context);
        toursDB = new ToursDB(context);
        stopsDB = new StopsDB(context);
    }

    public Guide get(Guide guide){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("guides", null, "id = ?",
                new String[] {String.valueOf(guide.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameGuide");
            int salaryIndex = c.getColumnIndex("salary");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            Guide gui = new Guide();
            gui.setId(c.getInt(idIndex));
            gui.setNameGuide(c.getString(nameIndex));
            gui.setSalary(c.getInt(salaryIndex));
            gui.setOperatorLogin(c.getString(operatorLoginIndex));
            if (gui.getOperatorLogin().equals(guide.getOperatorLogin())){
                dbHelper.close();
                return gui;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Guide guide){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameGuide", guide.getNameGuide());
        cv.put("salary", guide.getSalary());
        cv.put("operatorLogin", guide.getOperatorLogin());
        long guideId = db.insert("guides", null, cv);
        dbHelper.close();
    }

    public void update(Guide guide){
        if (get(guide) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameGuide", guide.getNameGuide());
        cv.put("salary", guide.getSalary());
        cv.put("operatorLogin", guide.getOperatorLogin());
        db.update("guides", cv, "id = ?", new String[] {String.valueOf(guide.getId())});
        dbHelper.close();
    }

    public void delete(Guide guide){
        if (get(guide) == null){
            return;
        }
        toursDB.delete(guide);
        stopsDB.delete(guide);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("guides", "id = " + guide.getId(), null);
        dbHelper.close();
    }

    public List<Guide> readAll(Operator operator){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Guide> retList = new ArrayList<Guide>();
        Cursor c = db.query("guides", null, "operatorLogin = ?",
                new String[] {operator.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameGuide");
            int salaryIndex = c.getColumnIndex("salary");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            do{
                Guide guide = new Guide();
                guide.setId(c.getInt(idIndex));
                guide.setNameGuide(c.getString(nameIndex));
                guide.setSalary(c.getInt(salaryIndex));
                guide.setOperatorLogin(c.getString(operatorLoginIndex));
                retList.add(guide);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Guide> readAllOperators(Operator operator){
        if (!Objects.equals(operator.getRole(), "admin")){
            return readAll(operator);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Guide> retList = new ArrayList<Guide>();
        Cursor c = db.query("guides", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameGuide");
            int salaryIndex = c.getColumnIndex("salary");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            do{
                Guide guide = new Guide();
                guide.setId(c.getInt(idIndex));
                guide.setNameGuide(c.getString(nameIndex));
                guide.setSalary(c.getInt(salaryIndex));
                guide.setOperatorLogin(c.getString(operatorLoginIndex));
                retList.add(guide);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseGuideDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table guides ("
                    + "id integer primary key autoincrement,"
                    + "nameGuide text,"
                    + "salary integer,"
                    + "operatorLogin text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
