package com.example.kursworkapplication.data;

public class Place {
    private int id;
    private int count;
    private String name;
    private String userLogin;
    private int excursion_id;

    @Override
    public String toString(){
        return String.format("Посещенное место: Id: %d, название: %s",
                id, name, count);
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
        return excursion_id;
    }

    public void setExcursion_id(int excursion_id) {
        this.excursion_id = excursion_id;
    }
}
