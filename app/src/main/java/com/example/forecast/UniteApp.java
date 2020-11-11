package com.example.forecast;

import android.app.Application;
import android.app.DownloadManager;

import com.example.forecast.DB.DBManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class UniteApp extends Application {
/*全局的地方*/

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }


}
