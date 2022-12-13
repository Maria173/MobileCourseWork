package com.example.kursworkapplication.data;

public class Excursion {
    private int id;
    private int calorie;
    private String wishes;
    private String userLogin;

    @Override
    public String toString(){
        return String.format("Excursion = {Id = %d, calorie = %d, wishes = %s}",
                id, calorie, wishes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
