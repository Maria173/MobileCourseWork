package com.example.kursworkapplication.data.Reports;

public class placesExcursions {
    private String excursion;
    private String place;

    public placesExcursions(){
        excursion = "";
        place = "";
    }

    public String getExcursion() {
        return excursion;
    }

    public void setExcursion(String excursion) {
        this.excursion = excursion;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
