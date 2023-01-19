package com.enesuzumcu.tabu.data.model;

public class Settings {
    public static Settings settings = null;

    private int time;
    private int teamCount;
    private int round;
    private int pass;

    public Settings(int time, int teamCount, int round, int pass) {
        this.time = time;
        this.teamCount = teamCount;
        this.round = round;
        this.pass = pass;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }
}
