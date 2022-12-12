package com.example.kursworkapplication.data;


import android.content.Context;

import com.example.kursworkapplication.data.DBs.LunchesDB;

import java.util.ArrayList;
import java.util.List;

public class LunchesData {
    private static ArrayList<Lunch> lunches  = new ArrayList<Lunch>();
    LunchesDB lunchesDB;

    public LunchesData(Context context, String userLogin){
        lunchesDB = new LunchesDB(context);
        readAll(userLogin);
    }
    public Lunch getLunch(int id, String login){
        Lunch lun = new Lunch();
        lun.setId(id);
        lun.setUserLogin(login);
        return lunchesDB.get(lun);
    }
    public List<Lunch> findAllLunches(String userLogin){
        return lunches;
    }
    public List<Lunch> findAllUsersLunches(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return lunchesDB.readAllUsers(ord);
    }
    public void addLunch(int price, int weight, String userLogin, int order_id){
        Lunch lunch = new Lunch();
        lunch.setUserLogin(userLogin);
        lunch.setPrice(price);
        lunch.setWeight(weight);
        lunch.setOrder_id(order_id);
        lunchesDB.add(lunch);
        readAll(userLogin);
    }
    public void updateLunch(int id, int price, int weight, String userLogin, int order_id){
        Lunch lunch = new Lunch();
        lunch.setId(id);
        lunch.setUserLogin(userLogin);
        lunch.setPrice(price);
        lunch.setWeight(weight);
        lunch.setOrder_id(order_id);
        lunchesDB.update(lunch);
        readAll(userLogin);
    }
    public void deleteLunch(int id, String userLogin){
        Lunch lunch = new Lunch();
        lunch.setId(id);
        lunch.setUserLogin(userLogin);
        lunchesDB.delete(lunch);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Lunch> lunchs = lunchesDB.readAll(usr);
        lunches.clear();
        for(Lunch lunch : lunchs){
            lunches.add(lunch);
        }
    }
    public List<Lunch> readAllLunches(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return lunchesDB.readAll(usr);
    }
}
