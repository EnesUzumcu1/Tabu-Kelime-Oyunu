package com.enesuzumcu.tabu.data.di;

import android.content.Context;

import com.enesuzumcu.tabu.data.local.database.DatabaseAccess;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DataBaseModule {

    @Singleton
    @Provides
    DatabaseAccess provideDatabaseAccess(@ApplicationContext Context appContext) {
        return DatabaseAccess.getInstance(appContext);
    }
}
