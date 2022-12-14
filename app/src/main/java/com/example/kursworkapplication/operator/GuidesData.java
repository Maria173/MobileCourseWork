package com.example.kursworkapplication.operator;

import android.content.Context;

import com.example.kursworkapplication.data.DBs.OrdersDB;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.operator.databaseOperator.GuidesDB;

import java.util.ArrayList;
import java.util.List;

public class GuidesData {
    private static ArrayList<Guide> guides  = new ArrayList<Guide>();
    GuidesDB guidesDB;

    public GuidesData(Context context, String userLogin){
        guidesDB = new GuidesDB(context);
        readAll(userLogin);
    }

    public Guide getGuide(int id, String login){
        Guide gui = new Guide();
        gui.setId(id);
        gui.setOperatorLogin(login);
        return guidesDB.get(gui);
    }
    public List<Guide> findAllGuides(String operatorLogin){
        return guides;
    }
    public List<Guide> findAllOperatorsGuides(String operatorLogin){
        Operator gui = new Operator();
        gui.setLogin(operatorLogin);
        return guidesDB.readAllOperators(gui);
    }

    public void addGuide(String nameGuide, int salary, String operatorLogin){
        Guide guide = new Guide();
        guide.setNameGuide(nameGuide);
        guide.setOperatorLogin(operatorLogin);
        guide.setSalary(salary);
        guidesDB.add(guide);
        readAll(operatorLogin);
    }
    public void updateGuide(int id, String nameGuide, int salary, String operatorLogin){
        Guide guide = new Guide();
        guide.setId(id);
        guide.setNameGuide(nameGuide);
        guide.setOperatorLogin(operatorLogin);
        guide.setSalary(salary);
        guidesDB.update(guide);
        readAll(operatorLogin);
    }
    public void deleteGuide(int id, String operatorLogin){
        Guide guide = new Guide();
        guide.setId(id);
        guide.setOperatorLogin(operatorLogin);
        guidesDB.delete(guide);
        readAll(operatorLogin);
    }
    private void readAll(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        List<Guide> guis = guidesDB.readAll(oper);
        guides.clear();
        for(Guide guide : guis){
            guides.add(guide);
        }
    }
    public List<Guide> readAllGuides(String operatorLogin){
        Operator oper = new Operator();
        oper.setLogin(operatorLogin);
        return guidesDB.readAll(oper);
    }
}
