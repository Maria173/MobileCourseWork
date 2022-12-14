package com.example.kursworkapplication.data.Reports;

public class tripsExcursions {
    private String trip;
    private String excursion;

    public tripsExcursions(){
        trip = "";
        excursion = "";
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getExcursion() {
        return excursion;
    }

    public void setExcursion(String excursion) {
        this.excursion = excursion;
    }
}
