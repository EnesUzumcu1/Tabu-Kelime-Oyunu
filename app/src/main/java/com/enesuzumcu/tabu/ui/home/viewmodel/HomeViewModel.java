package com.enesuzumcu.tabu.ui.home.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.enesuzumcu.tabu.R;
import com.enesuzumcu.tabu.data.model.Settings;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    @Inject
    public HomeViewModel(@ApplicationContext Context context) {
        checkSettings(context);
    }

    public void checkSettings(Context context) {
        if (Settings.settings == null) {
            Settings.settings = new Settings(
                    context.getResources().getInteger(R.integer.time),
                    context.getResources().getInteger(R.integer.teamCount),
                    context.getResources().getInteger(R.integer.roundCount),
                    context.getResources().getInteger(R.integer.pass)
            );
        }
    }
}
