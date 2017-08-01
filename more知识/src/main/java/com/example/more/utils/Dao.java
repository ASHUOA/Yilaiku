package com.example.more.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.more.SQLite;

/**
 * Created by fanyishuo on 2017/7/25.
 */

public class Dao {

    private final SQLiteDatabase dao;

    public Dao(Context context){
        SQLite sqLite=new SQLite(context);
        dao = sqLite.getWritableDatabase();
    }
    public boolean add(String title){
        ContentValues values=new ContentValues();
        values.put("title",title);
        long l = dao.insert("uses", null, values);
        if (l!=-1){
            return true;
        }else {
            return false;
        }
    }

}
