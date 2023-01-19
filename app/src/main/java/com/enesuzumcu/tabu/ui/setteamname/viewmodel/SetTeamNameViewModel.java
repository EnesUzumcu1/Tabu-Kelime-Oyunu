package com.enesuzumcu.tabu.ui.setteamname.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.enesuzumcu.tabu.R;
import com.enesuzumcu.tabu.data.model.Takimlar;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class SetTeamNameViewModel extends ViewModel {

    @Inject
    public SetTeamNameViewModel(@ApplicationContext Context context) {
        setTeams(context);
    }

    public void setTeams(Context context) {
        Takimlar.team1 = new Takimlar(context.getString(R.string.team1DefaultName), 0);
        Takimlar.team2 = new Takimlar(context.getString(R.string.team2DefaultName), 0);
        Takimlar.team3 = new Takimlar(context.getString(R.string.team3DefaultName), 0);
        Takimlar.team4 = new Takimlar(context.getString(R.string.team4DefaultName), 0);
    }

    public void setTeam1andTeam2Names(String team1Name,String team2Name){
        Takimlar.team1.setTakimAdi(team1Name);
        Takimlar.team2.setTakimAdi(team2Name);
    }

    public void setTeam3Name(String team3Name){
        Takimlar.team3.setTakimAdi(team3Name);
    }

    public void setTeam4Name(String team4Name){
        Takimlar.team4.setTakimAdi(team4Name);
    }

}

