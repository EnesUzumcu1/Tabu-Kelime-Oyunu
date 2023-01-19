package com.enesuzumcu.tabu.data.local.database;

import android.content.Context;

import com.enesuzumcu.tabu.utils.Constants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public DatabaseOpenHelper(Context context){
        super(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }
}
