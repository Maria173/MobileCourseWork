package com.example.kursworkapplication.operator;

import android.content.Context;

import com.example.kursworkapplication.data.DBs.LunchesDB;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.operator.databaseOperator.ToursDB;

import java.util.ArrayList;
import java.util.List;

public class ToursData {
    private static ArrayList<Tour> tours  = new ArrayList<Tour>();
    ToursDB toursDB;

    public ToursData(Context context, String operatorLogin){
        toursDB = new ToursDB(context);
        readAll(operatorLogin);
    }
    public Tour getTour(int id, String login){
        Tour tr = new Tour();
        tr.setId(id);
        tr.setOperatorLogin(login);
        return toursDB.get(tr);
    }
    public List<Tour> findAllTours(String operatorLogin){
        return tours;
    }
    public List<Tour> findAllOperatorsTours(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        return toursDB.readAllOperators(oper);
    }
    public void addTour(String name, String operatorLogin, int guide_id){
        Tour tour = new Tour();
        tour.setOperatorLogin(operatorLogin);
        tour.setName(name);
        tour.setGuide_id(guide_id);
        toursDB.add(tour);
        readAll(operatorLogin);
    }
    public void updateTour(int id, String name, String operatorLogin, int guide_id){
        Tour tour = new Tour();
        tour.setId(id);
        tour.setOperatorLogin(operatorLogin);
        tour.setName(name);
        tour.setGuide_id(guide_id);
        toursDB.update(tour);
        readAll(operatorLogin);
    }
    public void deleteTour(int id, String operatorLogin){
        Tour tour = new Tour();
        tour.setId(id);
        tour.setOperatorLogin(operatorLogin);
        toursDB.delete(tour);
        readAll(operatorLogin);
    }
    private void readAll(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        List<Tour> trs = toursDB.readAll(oper);
        tours.clear();
        for(Tour tour : trs){
            tours.add(tour);
        }
    }
    public List<Tour> readAllTours(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        return toursDB.readAll(oper);
    }
}
