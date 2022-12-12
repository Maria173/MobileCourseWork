package com.example.kursworkapplication.data;

import android.content.Context;

import com.example.kursworkapplication.OrdersActivity;
import com.example.kursworkapplication.data.DBs.OrdersDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersData {
    private static ArrayList<Order> orders  = new ArrayList<Order>();
    OrdersDB ordersDB;

    public OrdersData(Context context, String userLogin){
        ordersDB = new OrdersDB(context);
        readAll(userLogin);
    }

    public Order getOrder(int id, String login){
        Order ord = new Order();
        ord.setId(id);
        ord.setUserLogin(login);
        return ordersDB.get(ord);
    }
    public List<Order> findAllOrders(String userLogin){
        return orders;
    }
    public List<Order> findAllUsersOrders(String userLogin){
        User ord = new User();
        ord.setLogin(userLogin);
        return ordersDB.readAllUsers(ord);
    }

    public void addOrder(int calorie, String wishes, String userLogin){
        Order order = new Order();
        order.setCalorie(calorie);
        order.setUserLogin(userLogin);
        order.setWishes(wishes);
        ordersDB.add(order);
        readAll(userLogin);
    }
    public void updateOrder(int id, int calorie, String wishes, String userLogin){
        Order order = new Order();
        order.setId(id);
        order.setCalorie(calorie);
        order.setUserLogin(userLogin);
        order.setWishes(wishes);
        ordersDB.update(order);
        readAll(userLogin);
    }
    public void deleteOrder(int id, String userLogin){
        Order order = new Order();
        order.setId(id);
        order.setUserLogin(userLogin);
        ordersDB.delete(order);
        readAll(userLogin);
    }
    private void readAll(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        List<Order> ords = ordersDB.readAll(usr);
        orders.clear();
        for(Order order : ords){
            orders.add(order);
        }
    }
    public List<Order> readAllOrders(String userLogin){
        User usr = new User();
        usr.setLogin(userLogin);
        return ordersDB.readAll(usr);
    }
}
