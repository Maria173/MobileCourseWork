package com.example.kursworkapplication.data;

import java.time.LocalDateTime;

public class Trip {
    private int id;
    private String name;
    private int days;
    private String userLogin;
    private int excursion_id;

    @Override
    public String toString(){
        return String.format("Путешествие: Id: %d, название: %s",
                id, name, days);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String uerLogin) {
        this.userLogin = uerLogin;
    }

    public int getExcursion_id() {
        return excursion_id;
    }

    public void setExcursion_id(int excursion_id) {
        this.excursion_id = excursion_id;
    }
}
