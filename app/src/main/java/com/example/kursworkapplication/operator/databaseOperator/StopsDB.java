package com.example.kursworkapplication.operator.databaseOperator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.DBs.CutleriesDB;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.operator.Guide;
import com.example.kursworkapplication.operator.Operator;
import com.example.kursworkapplication.operator.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StopsDB {
    private StopsDB.DBHelper dbHelper;

    public StopsDB(Context context){
        dbHelper = new StopsDB.DBHelper(context);
    }

    public Stop get(Stop stop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("stops", null, "id = ?",
                new String[] {String.valueOf(stop.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int nameIndex = c.getColumnIndex("nameStop");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            Stop st = new Stop();
            st.setId(c.getInt(idIndex));
            st.setPrice(c.getInt(priIndex));
            st.setNameStop(c.getString(nameIndex));
            st.setOperatorLogin(c.getString(operatorLoginIndex));
            st.setGuide_id(c.getInt(guiIdIndex));
            if (st.getOperatorLogin().equals(stop.getOperatorLogin())){
                dbHelper.close();
                return st;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Stop stop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("price", stop.getPrice());
        cv.put("nameStop", stop.getNameStop());
        cv.put("operatorLogin", stop.getOperatorLogin());
        cv.put("guide_id", stop.getGuide_id());
        long stopId = db.insert("stops", null, cv);
        dbHelper.close();
    }

    public void update(Stop stop){
        if (get(stop) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("price", stop.getPrice());
        cv.put("nameStop", stop.getNameStop());
        cv.put("operatorLogin", stop.getOperatorLogin());
        cv.put("guide_id", stop.getGuide_id());
        db.update("stops", cv, "id = ?", new String[] {String.valueOf(stop.getId())});
        dbHelper.close();
    }

    public void delete(Stop stop){
        if(get(stop) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("stops", "id = " + stop.getId(), null);
        dbHelper.close();
    }

    public void delete(Guide guide){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("stops", "guide_id = " + guide.getId(), null);
        dbHelper.close();
    }

    public List<Stop> readAll(Operator operator){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Stop> retList = new ArrayList<Stop>();
        Cursor c = db.query("stops", null, "operatorLogin = ?",
                new String[] {operator.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int nameIndex = c.getColumnIndex("nameStop");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            do{
                Stop st = new Stop();
                st.setId(c.getInt(idIndex));
                st.setPrice(c.getInt(priIndex));
                st.setNameStop(c.getString(nameIndex));
                st.setOperatorLogin(c.getString(operatorLoginIndex));
                st.setGuide_id(c.getInt(guiIdIndex));
                retList.add(st);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Stop> readAllOperators(Operator operator){
        if (!Objects.equals(operator.getRole(), "admin")){
            return readAll(operator);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Stop> retList = new ArrayList<Stop>();
        Cursor c = db.query("stops", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("price");
            int nameIndex = c.getColumnIndex("nameStop");
            int operatorLoginIndex = c.getColumnIndex("operatorLogin");
            int guiIdIndex = c.getColumnIndex("guide_id");
            do{
                Stop st = new Stop();
                st.setId(c.getInt(idIndex));
                st.setPrice(c.getInt(priIndex));
                st.setNameStop(c.getString(nameIndex));
                st.setOperatorLogin(c.getString(operatorLoginIndex));
                st.setGuide_id(c.getInt(guiIdIndex));
                retList.add(st);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }



    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseStopDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table stops ("
                    + "id integer primary key autoincrement,"
                    + "price integer,"
                    + "nameStop text,"
                    + "operatorLogin text,"
                    + "guide_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
