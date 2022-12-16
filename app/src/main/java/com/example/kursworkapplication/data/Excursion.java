package com.example.kursworkapplication.data;

public class Excursion {
    private int id;
    private String name;
    private String type;
    private String userLogin;

    @Override
    public String toString(){
        return String.format("Экскурсия: Id: %d, название: %s",
                id, name, type);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
