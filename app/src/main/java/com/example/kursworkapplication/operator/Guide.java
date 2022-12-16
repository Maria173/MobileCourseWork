package com.example.kursworkapplication.operator;

public class Guide {
    private int id;
    private String nameGuide;
    private int salary;
    private String operatorLogin;

    @Override
    public String toString(){
        return String.format("Гид: Id = %d, имя: %s, зарплата: %d",
                id, nameGuide, salary);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGuide() {
        return nameGuide;
    }

    public void setNameGuide(String nameGuide) {
        this.nameGuide = nameGuide;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getOperatorLogin() {
        return operatorLogin;
    }

    public void setOperatorLogin(String operatorLogin) {
        this.operatorLogin = operatorLogin;
    }
}
