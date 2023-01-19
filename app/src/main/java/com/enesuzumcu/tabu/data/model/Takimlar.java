package com.enesuzumcu.tabu.data.model;

public class Takimlar {
    public static Takimlar team1, team2, team3, team4;

    private String takimAdi;
    private int takimPuani;

    public Takimlar(String takimAdi, int takimPuani) {
        this.takimAdi = takimAdi;
        this.takimPuani = takimPuani;
    }

    public Takimlar(){

    }

    public String getTakimAdi() {
        return takimAdi;
    }

    public void setTakimAdi(String takimAdi) {
        this.takimAdi = takimAdi;
    }

    public int getTakimPuani() {
        return takimPuani;
    }

    public void setTakimPuani(int takimPuani) {
        this.takimPuani = takimPuani;
    }
}
