package com.example.farhan.pushsum.entity;

public class Week {
    public int monday, tuesday, wednesday, thursday, friday, saturday, sunday, obbiettivo, massimo;
    private long id;

    public Week() {
        this.monday = 0;
        this.tuesday = 0;
        this.wednesday = 0;
        this.thursday = 0;
        this.friday = 0;
        this.saturday = 0;
        this.sunday = 0;
        this.obbiettivo = 5;
        this.massimo = 0;
    }

    public Week(long id, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday, int obbiettivo, int massimo) {
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.obbiettivo = obbiettivo;
        this.massimo = massimo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public int getObbiettivo() {
        return obbiettivo;
    }

    public void setObbiettivo(int obbiettivo) {
        this.obbiettivo = obbiettivo;
    }

    public int getMassimo() {
        return massimo;
    }

    public void setMassimo(int massimo) {
        this.massimo = massimo;
    }

    @Override
    public String toString() {
        return "Week{" +
                "id= " + id +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", obbiettivo=" + obbiettivo +
                ", massimo=" + massimo +
                '}';
    }
}