package com.example.kursworkapplication.data;

import android.content.Context;

import com.example.kursworkapplication.ExcursionsActivity;
import com.example.kursworkapplication.data.DBs.ExcursionsDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcursionsData {
    private static ArrayList<Excursion> excursions  = new ArrayList<Excursion>();
    ExcursionsDB excursionsDB;

    public ExcursionsData(Context context, String userLogin){
        excursionsDB = new ExcursionsDB(context);
        readAll(userLogin);
    }

    public Excursion getExcursion(int id, String login){
        Excursion ord = new Excursion();
        ord.setId(id);
        ord.setUserLogin(login);
        return excursionsDB.get(ord);
    }
    public List<Excursion> findAllExcursions(String userLogin){
        return excursions;
    }
    public List<Excursion> findAllUsersExcursions(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return excursionsDB.readAllUsers(ord);
    }

    public void addExcursion(int calorie, String wishes, String userLogin){
        Excursion excursion = new Excursion();
        excursion.setCalorie(calorie);
        excursion.setUserLogin(userLogin);
        excursion.setWishes(wishes);
        excursionsDB.add(excursion);
        readAll(userLogin);
    }
    public void updateExcursion(int id, int calorie, String wishes, String userLogin){
        Excursion excursion = new Excursion();
        excursion.setId(id);
        excursion.setCalorie(calorie);
        excursion.setUserLogin(userLogin);
        excursion.setWishes(wishes);
        excursionsDB.update(excursion);
        readAll(userLogin);
    }
    public void deleteExcursion(int id, String userLogin){
        Excursion excursion = new Excursion();
        excursion.setId(id);
        excursion.setUserLogin(userLogin);
        excursionsDB.delete(excursion);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Excursion> ords = excursionsDB.readAll(usr);
        excursions.clear();
        for(Excursion excursion : ords){
            excursions.add(excursion);
        }
    }
    public List<Excursion> readAllExcursions(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return excursionsDB.readAll(usr);
    }
}
