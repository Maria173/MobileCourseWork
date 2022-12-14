package com.example.kursworkapplication.operator;

import android.content.Context;

import com.example.kursworkapplication.operator.databaseOperator.StopsDB;

import java.util.ArrayList;
import java.util.List;

public class StopsData {
    private static ArrayList<Stop> stops  = new ArrayList<Stop>();
    StopsDB stopsDB;

    public StopsData(Context context, String operatorLogin){
        stopsDB = new StopsDB(context);
        readAll(operatorLogin);
    }
    public Stop getStop(int id, String login){
        Stop st = new Stop();
        st.setId(id);
        st.setOperatorLogin(login);
        return stopsDB.get(st);
    }
    public List<Stop> findAllStops(String operatorLogin){
        return stops;
    }
    public List<Stop> findAllOperatorsStops(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        return stopsDB.readAllOperators(oper);
    }
    public void addStop(int price, String nameStop, String operatorLogin, int guide_id){
        Stop stop = new Stop();
        stop.setOperatorLogin(operatorLogin);
        stop.setPrice(price);
        stop.setNameStop(nameStop);
        stop.setGuide_id(guide_id);
        stopsDB.add(stop);
        readAll(operatorLogin);
    }
    public void updateStop(int id, int price, String nameStop, String operatorLogin, int guide_id){
        Stop stop = new Stop();
        stop.setId(id);
        stop.setOperatorLogin(operatorLogin);
        stop.setPrice(price);
        stop.setNameStop(nameStop);
        stop.setGuide_id(guide_id);
        stopsDB.update(stop);
        readAll(operatorLogin);
    }
    public void deleteStop(int id, String operatorLogin){
        Stop stop = new Stop();
        stop.setId(id);
        stop.setOperatorLogin(operatorLogin);
        stopsDB.delete(stop);
        readAll(operatorLogin);
    }
    private void readAll(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        List<Stop> stps = stopsDB.readAll(oper);
        stops.clear();
        for(Stop stop : stps){
            stops.add(stop);
        }
        oper.setRole("admin");
        stps = stopsDB.readAllOperators(oper);
    }
    public List<Stop> readAllStops(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        return stopsDB.readAll(oper);
    }
}
