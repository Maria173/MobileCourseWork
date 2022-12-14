package com.example.kursworkapplication.operator.reportsOperator;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.kursworkapplication.data.CutleriesData;
import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.LunchesData;
import com.example.kursworkapplication.data.OrdersData;
import com.example.kursworkapplication.data.Reports.allUsersUnit;
import com.example.kursworkapplication.data.Reports.cutleriesOrders;
import com.example.kursworkapplication.data.Reports.lunchesOrders;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.data.UserData;
import com.example.kursworkapplication.operator.GuidesData;
import com.example.kursworkapplication.operator.Operator;
import com.example.kursworkapplication.operator.OperatorData;
import com.example.kursworkapplication.operator.Stop;
import com.example.kursworkapplication.operator.StopsData;
import com.example.kursworkapplication.operator.Tour;
import com.example.kursworkapplication.operator.ToursData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsOperatorLogic {
    StopsData stopsData;
    ToursData toursData;
    GuidesData guidesData;
    OperatorData operatorData;

    public ReportsOperatorLogic(Context context, String operatorLogin){
        stopsData = new StopsData(context, operatorLogin);
        toursData = new ToursData(context, operatorLogin);
        guidesData = new  GuidesData(context, operatorLogin);
        operatorData = new OperatorData(context);
    }

    public List<allOperatorsUnit> getAllOperatorsData(String login, String role){
        List<Operator> operators = operatorData.readAll(login, role);
        List<allOperatorsUnit> retList = new ArrayList<allOperatorsUnit>();
        for (Operator oper : operators){
            allOperatorsUnit operatorUnit = new allOperatorsUnit();
            operatorUnit.setLogin(oper.getLogin());
            operatorUnit.setRole(oper.getRole());
            operatorUnit.setGuidesCount(guidesData.readAllGuides(oper.getLogin()).size());
            operatorUnit.setStopsCount(stopsData.readAllStops(oper.getLogin()).size());
            operatorUnit.setToursCount(toursData.readAllTours(oper.getLogin()).size());
            retList.add(operatorUnit);
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<stopsGuides> getStopsByGuides(String login){
        List<Stop> stops = stopsData.findAllStops(login);
        stops.sort(Comparator.comparing(Stop::getGuide_id));
        List<stopsGuides> retList = new ArrayList<stopsGuides>();
        for(int i = 0; i < stops.size(); ++i){
            stopsGuides stGui = new stopsGuides();
            if (i == 0 || stops.get(i).getGuide_id() != stops.get(i - 1).getGuide_id()){
                stGui.setGuide(guidesData.getGuide(stops.get(i).getGuide_id(), login).toString());
            }
            stGui.setStop(stops.get(i).toString());
            retList.add(stGui);
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<toursGuides> getToursByGuides(String login, List<Integer> ord){
        List<Tour> tr = toursData.findAllTours(login);
        List<Tour> tours = new ArrayList<Tour>();
        for (Tour tour : tr){
            if (ord.contains(tour.getGuide_id())){
                tours.add(tour);
            }
        }
        tours.sort(Comparator.comparing(Tour::getGuide_id));
        List<toursGuides> retList = new ArrayList<>();
        for(int i = 0; i < tours.size(); ++i){
            toursGuides trGui = new toursGuides();
            if (i == 0 || tours.get(i).getGuide_id() != tours.get(i - 1).getGuide_id()){
                trGui.setGuide(guidesData.getGuide(tours.get(i).getGuide_id(), login).toString());
            }
            trGui.setTour(tours.get(i).toString());
            retList.add(trGui);
        }
        return retList;
    }
}
