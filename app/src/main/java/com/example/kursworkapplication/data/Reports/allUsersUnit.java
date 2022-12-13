package com.example.kursworkapplication.data.Reports;

public class allUsersUnit {
    private String login;
    private String role;
    private int tripsCount;
    private int excursionsCount;
    private int placesCount;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(int tripsCount) {
        this.tripsCount = tripsCount;
    }

    public int getExcursionsCount() {
        return excursionsCount;
    }

    public void setExcursionsCount(int excursionsCount) {
        this.excursionsCount = excursionsCount;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }
}
