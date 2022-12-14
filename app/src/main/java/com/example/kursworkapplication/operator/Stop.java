package com.example.kursworkapplication.operator;

public class Stop {
    private int id;
    private int price;
    private String nameStop;
    private String operatorLogin;
    private int guide_id;

    @Override
    public String toString(){
        return String.format("Stop = {Id = %d, price = %d, name = %s}",
                id, price, nameStop);
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

    public String getNameStop() {
        return nameStop;
    }

    public void setNameStop(String nameStop) {
        this.nameStop = nameStop;
    }

    public String getOperatorLogin() {
        return operatorLogin;
    }

    public void setOperatorLogin(String operatorLogin) {
        this.operatorLogin = operatorLogin;
    }

    public int getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }
}
