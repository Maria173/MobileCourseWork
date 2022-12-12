package com.example.kursworkapplication.data.Reports;

public class cutleriesOrders {
    private String order;
    private String cutlery;

    public cutleriesOrders(){
        order = "";
        cutlery = "";
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCutlery() {
        return cutlery;
    }

    public void setCutlery(String cutlery) {
        this.cutlery = cutlery;
    }
}
