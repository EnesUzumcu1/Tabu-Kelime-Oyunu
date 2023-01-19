package com.enesuzumcu.tabu.data.model;


public class Game {
    public static Game gameStatus = new Game(0, 5);

    private int score;
    private int pass;

    public Game(int score, int pass) {
        this.pass = pass;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

}
