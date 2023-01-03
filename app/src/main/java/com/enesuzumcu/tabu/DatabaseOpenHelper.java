package com.enesuzumcu.tabu;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public static final String DATABASE_NAME="TabuKelimeleri.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
}
