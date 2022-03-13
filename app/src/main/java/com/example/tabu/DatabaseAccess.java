package com.example.tabu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
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
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }
    public String getKelimeler(Integer id,Integer sutunSayisi){
        c=db.rawQuery("SELECT * FROM kelimeler WHERE ID = '"+id+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()){
            //sutunSayisi Databasede istedigimiz sutundaki degeri alıyor. sutunDegeri 1 de ID var.
            String address = c.getString(sutunSayisi);
            buffer.append(""+address);
        }
        c.close();
        return buffer.toString();
    }
    public int databaseUzunlugu(){
        c=db.rawQuery("SELECT count(*) FROM kelimeler WHERE ID",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String uzunluk = c.getString(0);
            buffer.append(""+uzunluk);
        }
        c.close();
        return Integer.parseInt(buffer.toString());
    }
}
