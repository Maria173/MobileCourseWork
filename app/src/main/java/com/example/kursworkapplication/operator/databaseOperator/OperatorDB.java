package com.example.kursworkapplication.operator.databaseOperator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kursworkapplication.operator.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperatorDB {
    private OperatorDB.DBHelper dbHelper;

    public OperatorDB(Context context){
        dbHelper = new OperatorDB.DBHelper(context);
    }

    public boolean registration(Operator operator){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { operator.getLogin() };
        Cursor c = db.query("operators", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                dbHelper.close();
                return false;
            }
            ContentValues cv = new ContentValues();
            cv.put("login", operator.getLogin());
            cv.put("password", operator.getPassword());
            cv.put("role", operator.getRole());
            long operatorId =  db.insert("operators", null, cv);
            dbHelper.close();
            return true;
        }
        dbHelper.close();
        return false;
    }

    public Operator authorization(Operator operator){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { operator.getLogin() };
        Cursor c = db.query("operators", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                if (Objects.equals(operator.getLogin(), c.getString(1)) &&
                        Objects.equals(operator.getPassword(), c.getString(2))){
                    Operator retOperator = new Operator();
                    retOperator.setLogin(operator.getLogin());
                    retOperator.setPassword(operator.getPassword());
                    retOperator.setRole(c.getString(3));
                    c.close();
                    db.close();
                    return retOperator;
                }
            }
            c.close();
            dbHelper.close();
            return null;
        }
        dbHelper.close();
        return null;
    }

    public List<Operator> readAll(Operator operator) {
        if (!Objects.equals(operator.getRole(), "admin")) {
            return null;
        }
        List<Operator> retList = new ArrayList<Operator>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("operators", null, null,
                null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex("id");
            int logIndex = c.getColumnIndex("login");
            int rolIndex = c.getColumnIndex("role");
            do {
                Operator usr = new Operator();
                usr.setId(c.getInt(idIndex));
                usr.setRole(c.getString(rolIndex));
                usr.setLogin(c.getString(logIndex));
                retList.add(usr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseOperatorDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table operators ("
                    + "id integer primary key autoincrement,"
                    + "login text,"
                    + "password text,"
                    + "role text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
