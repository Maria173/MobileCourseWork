package com.example.kursworkapplication.data;

import android.content.Context;

import com.example.kursworkapplication.data.DBs.CutleriesDB;

import java.util.ArrayList;
import java.util.List;

public class CutleriesData {
    private static ArrayList<Cutlery> cutleries  = new ArrayList<Cutlery>();
    CutleriesDB cutleriesDB;

    public CutleriesData(Context context, String userLogin){
        cutleriesDB = new CutleriesDB(context);
        readAll(userLogin);
    }
    public Cutlery getcutlery(int id, String login){
        Cutlery lun = new Cutlery();
        lun.setId(id);
        lun.setUserLogin(login);
        return cutleriesDB.get(lun);
    }
    public List<Cutlery> findAllcutleries(String userLogin){
        return cutleries;
    }
    public List<Cutlery> findAllUserscutleries(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return cutleriesDB.readAllUsers(ord);
    }
    public void addcutlery(int count, String name, String userLogin, int order_id){
        Cutlery cutlery = new Cutlery();
        cutlery.setUserLogin(userLogin);
        cutlery.setCount(count);
        cutlery.setName(name);
        cutlery.setOrder_id(order_id);
        cutleriesDB.add(cutlery);
        readAll(userLogin);
    }
    public void updatecutlery(int id, int count, String name, String userLogin, int order_id){
        Cutlery cutlery = new Cutlery();
        cutlery.setId(id);
        cutlery.setUserLogin(userLogin);
        cutlery.setCount(count);
        cutlery.setName(name);
        cutlery.setOrder_id(order_id);
        cutleriesDB.update(cutlery);
        readAll(userLogin);
    }
    public void deletecutlery(int id, String userLogin){
        Cutlery cutlery = new Cutlery();
        cutlery.setId(id);
        cutlery.setUserLogin(userLogin);
        cutleriesDB.delete(cutlery);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Cutlery> cutlerys = cutleriesDB.readAll(usr);
        cutleries.clear();
        for(Cutlery cutlery : cutlerys){
            cutleries.add(cutlery);
        }
        usr.setRole("admin");
        cutlerys = cutleriesDB.readAllUsers(usr);
    }
    public List<Cutlery> readAllCutleries(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return cutleriesDB.readAll(usr);
    }
}
