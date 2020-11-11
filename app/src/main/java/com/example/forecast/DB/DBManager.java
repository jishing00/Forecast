package com.example.forecast.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager  {
    public static SQLiteDatabase database;

    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public static List<String> queryAllCityName(){ //SQL查詢全部縣市
        Cursor cursor = database.query("info", null, null, null, null, null,null);
        List<String>cityList = new ArrayList<>();

        while (cursor.moveToNext()) { //只要還有下一筆, 儲存之。
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }

        return cityList;
    }
    public static int updateInfoByCity(String city,String content){ //根據名稱，置換內容
        ContentValues  values = new ContentValues();
        values.put("content",content);

        return database.update("info",values,"city=?",new String[]{city});
    }
    public static long addCityInfo(String city,String content){ //新增一筆縣市
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);

        return database.insert("info",null,values);
    }
    public static String queryInfoByCity(String city){ //依縣市名稱尋找
        Cursor cursor = database.query("info", null, "city=?", new String[]{city}, null, null,null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            String content = cursor.getString(cursor.getColumnIndex("content"));

            return content;
        }
        return null;
    }
}
