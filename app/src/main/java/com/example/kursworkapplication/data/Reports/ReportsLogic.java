package com.example.kursworkapplication.data.Reports;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.kursworkapplication.data.PlacesData;
import com.example.kursworkapplication.data.Place;
import com.example.kursworkapplication.data.Trip;
import com.example.kursworkapplication.data.TripsData;
import com.example.kursworkapplication.data.Excursion;
import com.example.kursworkapplication.data.ExcursionsData;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.data.UserData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsLogic {
    PlacesData placesData;
    TripsData tripsData;
    ExcursionsData excursionsData;
    UserData userData;

    public ReportsLogic(Context context, String userLogin){
        placesData = new PlacesData(context, userLogin);
        tripsData = new TripsData(context, userLogin);
        excursionsData = new  ExcursionsData(context, userLogin);
        userData = new UserData(context);
    }

    public List<allUsersUnit> getAllUsersData(String login, String role){
        List<User> users = userData.readAll(login, role);
        List<allUsersUnit> retList = new ArrayList<allUsersUnit>();
        for (User usr : users){
            allUsersUnit userUnit = new allUsersUnit();
            userUnit.setLogin(usr.getLogin());
            userUnit.setRole(usr.getRole());
            userUnit.setExcursionsCount(excursionsData.readAllExcursions(usr.getLogin()).size());
            userUnit.setPlacesCount(placesData.readAllPlaces(usr.getLogin()).size());
            userUnit.setTripsCount(tripsData.readAllTrips(usr.getLogin()).size());
            retList.add(userUnit);
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<placesExcursions> getPlacesByExcursions(String login){
        List<Place> places = placesData.findAllplaces(login);
        places.sort(Comparator.comparing(Place::getExcursion_id));
        List<placesExcursions> retList = new ArrayList<placesExcursions>();
        for(int i = 0; i < places.size(); ++i){
            placesExcursions cut = new placesExcursions();
            if (i == 0 || places.get(i).getExcursion_id() != places.get(i - 1).getExcursion_id()){
                cut.setExcursion(excursionsData.getExcursion(places.get(i).getExcursion_id(), login).toString());
            }
            cut.setPlace(places.get(i).toString());
            retList.add(cut);
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<tripsExcursions> getTripsByExcursions(String login, List<Integer> ord){
        List<Trip> ln = tripsData.findAllTrips(login);
        List<Trip> trips = new ArrayList<Trip>();
        for (Trip trip : ln){
            if (ord.contains(trip.getExcursion_id())){
                trips.add(trip);
            }
        }
        trips.sort(Comparator.comparing(Trip::getExcursion_id));
        List<tripsExcursions> retList = new ArrayList<>();
        for(int i = 0; i < trips.size(); ++i){
            tripsExcursions lnd = new tripsExcursions();
            if (i == 0 || trips.get(i).getExcursion_id() != trips.get(i - 1).getExcursion_id()){
                lnd.setExcursion(excursionsData.getExcursion(trips.get(i).getExcursion_id(), login).toString());
            }
            lnd.setTrip(trips.get(i).toString());
            retList.add(lnd);
        }
        return retList;
    }
}
