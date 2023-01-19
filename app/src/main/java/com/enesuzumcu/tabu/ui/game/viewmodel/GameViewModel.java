package com.enesuzumcu.tabu.ui.game.viewmodel;

import androidx.lifecycle.ViewModel;

import com.enesuzumcu.tabu.data.model.Settings;
import com.enesuzumcu.tabu.data.model.Takimlar;
import com.enesuzumcu.tabu.data.model.Words;
import com.enesuzumcu.tabu.domain.repository.DatabaseRepository;
import com.enesuzumcu.tabu.data.model.Game;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GameViewModel extends ViewModel {
    private final DatabaseRepository repository;
    private final ArrayList<Integer> randomNumbers = new ArrayList<>();
    private final Random _random = new Random();
    private final int databaseLength;
    public int turn = 0;
    public final int maxTurn;
    public int second;
    public Game gameStatus;
    public boolean inTheGame = false;
    public Words words;

    @Inject
    public GameViewModel(DatabaseRepository repository) {
        this.repository = repository;
        gameStatus = Game.gameStatus;
        databaseLength = getLength();
        maxTurn = (Settings.settings.getTeamCount() * Settings.settings.getRound()) - 1;
        second = Settings.settings.getTime();
        getWords();
    }

    public void getWords() {
        words = repository.getWords(randomNumberGenerator(databaseLength));
    }

    public int getLength() {
        return repository.databaseLength();
    }

    public void increaseScore() {
        gameStatus.setScore(gameStatus.getScore() + 1);
    }

    public void decreaseScore() {
        gameStatus.setScore(gameStatus.getScore() - 1);
    }

    public void decreasePass() {
        if(gameStatus.getPass() > 0) gameStatus.setPass(gameStatus.getPass() - 1);
    }

    public void decreaseSecond() {
        second--;
        inTheGame = true;
    }

    public void resetState() {
        gameStatus.setScore(0);
        gameStatus.setPass(Settings.settings.getPass());
        second = Settings.settings.getTime();
        inTheGame = false;
        getWords();
    }

    public void increaseTurn() {
        turn++;
    }

    public String getTeamName() {
        return getTeam().getTakimAdi();
    }

    public Takimlar getTeam() {
        switch (turn % Settings.settings.getTeamCount()) {
            case 0:
                return Takimlar.team1;
            case 1:
                return Takimlar.team2;
            case 2:
                return Takimlar.team3;
            case 3:
                return Takimlar.team4;
            default:
                return null;
        }
    }

    public int updateScore() {
        Takimlar team = getTeam();
        team.setTakimPuani(gameStatus.getScore() + team.getTakimPuani());
        return team.getTakimPuani();
    }

    public String winner() {
        switch (Settings.settings.getTeamCount()) {
            case 2: {
                if (Takimlar.team1.getTakimPuani() > Takimlar.team2.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team1.getTakimAdi());
                }
                if (Takimlar.team2.getTakimPuani() > Takimlar.team1.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team2.getTakimAdi());
                }
                break;
            }
            case 3: {
                if (Takimlar.team1.getTakimPuani() > Takimlar.team2.getTakimPuani()
                        && Takimlar.team1.getTakimPuani() > Takimlar.team3.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team1.getTakimAdi());
                }
                if (Takimlar.team2.getTakimPuani() > Takimlar.team1.getTakimPuani()
                        && Takimlar.team2.getTakimPuani() > Takimlar.team3.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team2.getTakimAdi());
                }
                if (Takimlar.team3.getTakimPuani() > Takimlar.team1.getTakimPuani()
                        && Takimlar.team3.getTakimPuani() > Takimlar.team2.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team3.getTakimAdi());
                }
                break;
            }
            case 4: {
                if (Takimlar.team1.getTakimPuani() > Takimlar.team2.getTakimPuani()
                        && Takimlar.team1.getTakimPuani() > Takimlar.team3.getTakimPuani()
                        && Takimlar.team1.getTakimPuani() > Takimlar.team4.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team1.getTakimAdi());
                }
                if (Takimlar.team2.getTakimPuani() > Takimlar.team1.getTakimPuani()
                        && Takimlar.team2.getTakimPuani() > Takimlar.team3.getTakimPuani()
                        && Takimlar.team2.getTakimPuani() > Takimlar.team4.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team2.getTakimAdi());
                }
                if (Takimlar.team3.getTakimPuani() > Takimlar.team1.getTakimPuani()
                        && Takimlar.team3.getTakimPuani() > Takimlar.team2.getTakimPuani()
                        && Takimlar.team3.getTakimPuani() > Takimlar.team4.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team3.getTakimAdi());
                }
                if (Takimlar.team4.getTakimPuani() > Takimlar.team1.getTakimPuani()
                        && Takimlar.team4.getTakimPuani() > Takimlar.team2.getTakimPuani()
                        && Takimlar.team4.getTakimPuani() > Takimlar.team3.getTakimPuani()) {
                    return String.format("Kazanan <%s> Tebrikler!", Takimlar.team4.getTakimAdi());
                }
                break;
            }
        }
        return "Kazanan Yok.";
    }


    public int randomNumberGenerator(int length) {
        boolean loopControl = true;
        int randomNumber = _random.nextInt(length + 1);
        if (!randomNumbers.contains(randomNumber)) {
            randomNumbers.add(randomNumber);
            loopControl = false;
        }
        while (loopControl) {
            randomNumber = _random.nextInt(length + 1);
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
                loopControl = false;
                break;
            }
        }
        return randomNumber;
    }

}
