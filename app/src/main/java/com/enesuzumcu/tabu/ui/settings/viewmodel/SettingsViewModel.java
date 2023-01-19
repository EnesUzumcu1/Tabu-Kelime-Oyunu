package com.enesuzumcu.tabu.ui.settings.viewmodel;

import androidx.lifecycle.ViewModel;

import com.enesuzumcu.tabu.data.model.Settings;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private int tempTeamCount = 0, tempTime = 0, tempRoundCount = 0, tempPass = 0;

    @Inject
    public SettingsViewModel(){}

    public void setTeamCount(int teamCount){
        tempTeamCount = teamCount;
    }

    public void setTime(int time){
        tempTime = time;
    }

    public void setRoundCount(int roundCount){
        tempRoundCount = roundCount;
    }

    public void setPass(int pass){
        tempPass = pass;
    }

    public void save(){
        if(Settings.settings == null){
            Settings.settings = new Settings(tempTime,tempTeamCount,tempRoundCount,tempPass);
        }else{
            Settings.settings.setTime(tempTime);
            Settings.settings.setTeamCount(tempTeamCount);
            Settings.settings.setRound(tempRoundCount);
            Settings.settings.setPass(tempPass);
        }
    }

}
