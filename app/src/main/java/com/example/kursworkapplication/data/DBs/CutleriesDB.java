package com.example.kursworkapplication.data.DBs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CutleriesDB {
    private DBHelper dbHelper;

    public CutleriesDB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Cutlery get(Cutlery cutlery){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("cutleries", null, "id = ?",
                new String[] {String.valueOf(cutlery.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            Cutlery cut = new Cutlery();
            cut.setId(c.getInt(idIndex));
            cut.setCount(c.getInt(priIndex));
            cut.setName(c.getString(weiIndex));
            cut.setUserLogin(c.getString(userLoginIndex));
            cut.setOrder_id(c.getInt(ordIdIndex));
            if (cut.getUserLogin().equals(cutlery.getUserLogin())){
                dbHelper.close();
                return cut;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Cutlery cutlery){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("count", cutlery.getCount());
        cv.put("name", cutlery.getName());
        cv.put("userLogin", cutlery.getUserLogin());
        cv.put("order_id", cutlery.getOrder_id());
        long cutleryId = db.insert("cutleries", null, cv);
        dbHelper.close();
    }

    public void update(Cutlery cutlery){
        if (get(cutlery) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("count", cutlery.getCount());
        cv.put("name", cutlery.getName());
        cv.put("userLogin", cutlery.getUserLogin());
        cv.put("order_id", cutlery.getOrder_id());
        db.update("cutleries", cv, "id = ?", new String[] {String.valueOf(cutlery.getId())});
        dbHelper.close();
    }

    public void delete(Cutlery cutlery){
        if(get(cutlery) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cutleries", "id = " + cutlery.getId(), null);
        dbHelper.close();
    }

    public void delete(Order order){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cutleries", "order_id = " + order.getId(), null);
        dbHelper.close();
    }

    public List<Cutlery> readAll(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Cutlery> retList = new ArrayList<Cutlery>();
        Cursor c = db.query("cutleries", null, "userLogin = ?",
                new String[] {user.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            do{
                Cutlery cut = new Cutlery();
                cut.setId(c.getInt(idIndex));
                cut.setCount(c.getInt(priIndex));
                cut.setName(c.getString(weiIndex));
                cut.setUserLogin(c.getString(userLoginIndex));
                cut.setOrder_id(c.getInt(ordIdIndex));
                retList.add(cut);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Cutlery> readAllUsers(User user){
        if (!Objects.equals(user.getRole(), "admin")){
            return readAll(user);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Cutlery> retList = new ArrayList<Cutlery>();
        Cursor c = db.query("cutleries", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int priIndex = c.getColumnIndex("count");
            int weiIndex = c.getColumnIndex("name");
            int userLoginIndex = c.getColumnIndex("userLogin");
            int ordIdIndex = c.getColumnIndex("order_id");
            do{
                Cutlery lun = new Cutlery();
                lun.setId(c.getInt(idIndex));
                lun.setCount(c.getInt(priIndex));
                lun.setName(c.getString(weiIndex));
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
            super(context, "kursDBCutleries", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table cutleries ("
                    + "id integer primary key autoincrement,"
                    + "count integer,"
                    + "name text,"
                    + "userLogin text,"
                    + "order_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
