package com.example.kursworkapplication.data.Reports;

public class lunchesOrders {
    private String lunch;
    private String order;

    public lunchesOrders(){
        lunch = "";
        order = "";
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
