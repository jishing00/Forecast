package com.example.forecast.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Forecast.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //創建表
        String sql="create table info(_id integer primary key autoincrement, " +
                "city varchar(20) unique not null, " +
                "content  text not null)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
