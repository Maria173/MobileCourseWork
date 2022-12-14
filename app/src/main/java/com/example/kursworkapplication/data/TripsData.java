package com.example.kursworkapplication.data;


import android.content.Context;

import com.example.kursworkapplication.data.DBs.TripsDB;

import java.util.ArrayList;
import java.util.List;

public class TripsData {
    private static ArrayList<Trip> trips  = new ArrayList<Trip>();
    TripsDB tripsDB;

    public TripsData(Context context, String userLogin){
        tripsDB = new TripsDB(context);
        readAll(userLogin);
    }
    public Trip getTrip(int id, String login){
        Trip lun = new Trip();
        lun.setId(id);
        lun.setUserLogin(login);
        return tripsDB.get(lun);
    }
    public List<Trip> findAllTrips(String userLogin){
        return trips;
    }
    public List<Trip> findAllUsersTrips(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return tripsDB.readAllUsers(ord);
    }
    public void addTrip(String name, int days, String userLogin, int excursion_id){
        Trip trip = new Trip();
        trip.setUserLogin(userLogin);
        trip.setName(name);
        trip.setDays(days);
        trip.setExcursion_id(excursion_id);
        tripsDB.add(trip);
        readAll(userLogin);
    }
    public void updateTrip(int id, String name, int days, String userLogin, int excursion_id){
        Trip trip = new Trip();
        trip.setId(id);
        trip.setUserLogin(userLogin);
        trip.setName(name);
        trip.setDays(days);
        trip.setExcursion_id(excursion_id);
        tripsDB.update(trip);
        readAll(userLogin);
    }
    public void deleteTrip(int id, String userLogin){
        Trip trip = new Trip();
        trip.setId(id);
        trip.setUserLogin(userLogin);
        tripsDB.delete(trip);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Trip> trps = tripsDB.readAll(usr);
        trips.clear();
        for(Trip trip : trps){
            trips.add(trip);
        }
    }
    public List<Trip> readAllTrips(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return tripsDB.readAll(usr);
    }
}
