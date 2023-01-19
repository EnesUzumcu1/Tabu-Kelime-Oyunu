package com.enesuzumcu.tabu.data.local.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enesuzumcu.tabu.data.model.Words;
import com.enesuzumcu.tabu.utils.Constants;

public class DatabaseAccess {
    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance ==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getReadableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }
    @SuppressLint("Range")
    public Words getWords(Integer id){
        open();
        c=db.rawQuery("SELECT * FROM '"+Constants.TABLE_NAME+"' WHERE ID = '"+id+"'",new String[]{});
        Words words = new Words();
        if (c.moveToFirst()){
            words.AnlatilanKelime = c.getString(c.getColumnIndex(Constants.COL2));
            words.Tabu1 = c.getString(c.getColumnIndex(Constants.COL3));
            words.Tabu2 = c.getString(c.getColumnIndex(Constants.COL4));
            words.Tabu3 = c.getString(c.getColumnIndex(Constants.COL5));
            words.Tabu4 = c.getString(c.getColumnIndex(Constants.COL6));
            words.Tabu5 = c.getString(c.getColumnIndex(Constants.COL7));
        }
        c.close();
        close();
        return words;
    }
    @SuppressLint("Range")
    public int databaseLength(){
        open();
        c=db.rawQuery("SELECT count(*) AS length FROM '"+Constants.TABLE_NAME+"' WHERE ID",new String[]{});
        int length = 0;
        if(c.moveToFirst()){
            length = Integer.parseInt(c.getString(c.getColumnIndex("length")));
        }
        c.close();
        close();
        return length;
    }
}
