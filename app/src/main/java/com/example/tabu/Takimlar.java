package com.example.tabu;

public class Takimlar {
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
