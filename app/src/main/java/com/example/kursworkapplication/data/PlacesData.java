package com.example.kursworkapplication.data;

import android.content.Context;

import com.example.kursworkapplication.data.DBs.PlacesDB;

import java.util.ArrayList;
import java.util.List;

public class PlacesData {
    private static ArrayList<Place> places  = new ArrayList<Place>();
    PlacesDB placesDB;

    public PlacesData(Context context, String userLogin){
        placesDB = new PlacesDB(context);
        readAll(userLogin);
    }
    public Place getplace(int id, String login){
        Place lun = new Place();
        lun.setId(id);
        lun.setUserLogin(login);
        return placesDB.get(lun);
    }
    public List<Place> findAllplaces(String userLogin){
        return places;
    }
    public List<Place> findAllUsersplaces(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return placesDB.readAllUsers(ord);
    }
    public void addplace(int count, String name, String userLogin, int excursion_id){
        Place place = new Place();
        place.setUserLogin(userLogin);
        place.setCount(count);
        place.setName(name);
        place.setExcursion_id(excursion_id);
        placesDB.add(place);
        readAll(userLogin);
    }
    public void updateplace(int id, int count, String name, String userLogin, int excursion_id){
        Place place = new Place();
        place.setId(id);
        place.setUserLogin(userLogin);
        place.setCount(count);
        place.setName(name);
        place.setExcursion_id(excursion_id);
        placesDB.update(place);
        readAll(userLogin);
    }
    public void deleteplace(int id, String userLogin){
        Place place = new Place();
        place.setId(id);
        place.setUserLogin(userLogin);
        placesDB.delete(place);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Place> places = placesDB.readAll(usr);
        places.clear();
        for(Place place : places){
            places.add(place);
        }
        usr.setRole("admin");
        places = placesDB.readAllUsers(usr);
    }
    public List<Place> readAllPlaces(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return placesDB.readAll(usr);
    }
}
