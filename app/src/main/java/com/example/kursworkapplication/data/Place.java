package com.example.kursworkapplication.data;

public class Place {
    private int id;
    private int count;
    private String name;
    private String userLogin;
    private int order_id;

    @Override
    public String toString(){
        return String.format("Place = {Id = %d, count = %d, name = %s}",
                id, count, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getExcursion_id() {
        return order_id;
    }

    public void setExcursion_id(int order_id) {
        this.order_id = order_id;
    }
}
