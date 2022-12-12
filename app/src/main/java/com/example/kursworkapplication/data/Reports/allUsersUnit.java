package com.example.kursworkapplication.data.Reports;

public class allUsersUnit {
    private String login;
    private String role;
    private int lunchesCount;
    private int ordersCount;
    private int cutleriesCount;

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

    public int getLunchesCount() {
        return lunchesCount;
    }

    public void setLunchesCount(int lunchesCount) {
        this.lunchesCount = lunchesCount;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public int getCutleriesCount() {
        return cutleriesCount;
    }

    public void setCutleriesCount(int cutleriesCount) {
        this.cutleriesCount = cutleriesCount;
    }
}
