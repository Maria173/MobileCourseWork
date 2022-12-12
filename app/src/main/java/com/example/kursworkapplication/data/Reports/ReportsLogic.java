package com.example.kursworkapplication.data.Reports;


import android.content.Context;

import com.example.kursworkapplication.data.CutleriesData;
import com.example.kursworkapplication.data.Cutlery;
import com.example.kursworkapplication.data.Lunch;
import com.example.kursworkapplication.data.LunchesData;
import com.example.kursworkapplication.data.Order;
import com.example.kursworkapplication.data.OrdersData;
import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.data.UserData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsLogic {
    CutleriesData cutleriesData;
    LunchesData lunchesData;
    OrdersData ordersData;
    UserData userData;

    public ReportsLogic(Context context, String userLogin){
        cutleriesData = new CutleriesData(context, userLogin);
        lunchesData = new LunchesData(context, userLogin);
        ordersData = new  OrdersData(context, userLogin);
        userData = new UserData(context);
    }

    public List<allUsersUnit> getAllUsersData(String login, String role){
        List<User> users = userData.readAll(login, role);
        List<allUsersUnit> retList = new ArrayList<allUsersUnit>();
        for (User usr : users){
            allUsersUnit userUnit = new allUsersUnit();
            userUnit.setLogin(usr.getLogin());
            userUnit.setRole(usr.getRole());
            userUnit.setOrdersCount(ordersData.readAllOrders(usr.getLogin()).size());
            userUnit.setCutleriesCount(cutleriesData.readAllCutleries(usr.getLogin()).size());
            userUnit.setLunchesCount(lunchesData.readAllLunches(usr.getLogin()).size());
            retList.add(userUnit);
        }
        return retList;
    }

    public List<cutleriesOrders> getCutleriesByOrders(String login){
        List<Cutlery> cutleries = cutleriesData.findAllcutleries(login);
        cutleries.sort(Comparator.comparing(Cutlery::getOrder_id));
        List<cutleriesOrders> retList = new ArrayList<cutleriesOrders>();
        for(int i = 0; i < cutleries.size(); ++i){
            cutleriesOrders cut = new cutleriesOrders();
            if (i == 0 || cutleries.get(i).getOrder_id() != cutleries.get(i - 1).getOrder_id()){
                cut.setOrder(ordersData.getOrder(cutleries.get(i).getOrder_id(), login).toString());
            }
            cut.setCutlery(cutleries.get(i).toString());
            retList.add(cut);
        }
        return retList;
    }

    public List<lunchesOrders> getLunchesByOrders(String login, List<Integer> ord){
        List<Lunch> ln = lunchesData.findAllLunches(login);
        List<Lunch> lunches = new ArrayList<Lunch>();
        for (Lunch lunch : ln){
            if (ord.contains(lunch.getOrder_id())){
                lunches.add(lunch);
            }
        }
        lunches.sort(Comparator.comparing(Lunch::getOrder_id));
        List<lunchesOrders> retList = new ArrayList<>();
        for(int i = 0; i < lunches.size(); ++i){
            lunchesOrders lnd = new lunchesOrders();
            if (i == 0 || lunches.get(i).getOrder_id() != lunches.get(i - 1).getOrder_id()){
                lnd.setOrder(ordersData.getOrder(lunches.get(i).getOrder_id(), login).toString());
            }
            lnd.setLunch(lunches.get(i).toString());
            retList.add(lnd);
        }
        return retList;
    }
}
