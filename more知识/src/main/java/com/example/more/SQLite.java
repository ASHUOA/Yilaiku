package com.example.more;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanyishuo on 2017/7/25.
 */

public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context) {
        super(context, "use.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table uses(id Integer primary key autoincrement,title varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
