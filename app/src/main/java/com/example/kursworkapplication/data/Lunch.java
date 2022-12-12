package com.example.kursworkapplication.data;

import java.time.LocalDateTime;

public class Lunch {
    private int id;
    private int price;
    private int weight;
    private String userLogin;
    private int order_id;

    @Override
    public String toString(){
        return String.format("Lunch = {Id = %d, price = %d, weight = %d}",
                id, price, weight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String uerLogin) {
        this.userLogin = uerLogin;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
