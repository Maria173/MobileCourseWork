package com.example.kursworkapplication.operator;

public class Tour {
    private int id;
    private String name;
    private String operatorLogin;
    private int guide_id;

    @Override
    public String toString(){
        return String.format("Tour = {Id = %d, name = %s}",
                id, name);
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
