package com.example.forecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.forecast.DB.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView addcityIV, moreIV;
    LinearLayout pointlayout;
    ViewPager mainVP;
    //ViewPage的數據源
    List<Fragment>fragmentList;
    //要顯示的城市集合
    List<String>cityList;
    //ViewPager的頁數指示器
    List<ImageView>imgList;

    private CityFragmentAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addcityIV = findViewById(R.id.main_iv_add);
        moreIV = findViewById(R.id.main_iv_more);
        pointlayout = findViewById(R.id.main_l_layout_point);
        mainVP = findViewById(R.id.main_dv);

        fragmentList = new ArrayList<>();
        cityList = DBManager.queryAllCityName();//獲取資料庫內"城市"的資料
        imgList = new ArrayList<>();
        //添加點擊事件
        addcityIV.setOnClickListener(this);
        moreIV.setOnClickListener(this);

        if(cityList.size()==0){
            cityList.add("臺北市");
            cityList.add("新北市");
            cityList.add("嘉義市");
        }
        //初始化
        initPV();
        adaptor = new CityFragmentAdaptor(getSupportFragmentManager(), fragmentList);
        mainVP.setAdapter(adaptor);
        //小圓點
        initPoint();
        //最後添加的城市信息
        mainVP.setCurrentItem(fragmentList.size()-1);
        //設置VP監聽器
        setPagerListener();
    }

    private void setPagerListener() {
        mainVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0; i<imgList.size();i++){
                    imgList.get(i).setImageResource(R.mipmap.a1);
                }
                imgList.get(position).setImageResource(R.mipmap.a2);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initPoint() {
        //頁面指示器
        for (int i = 0;i< fragmentList.size();i++){
            ImageView pIV  = new ImageView(this);
            pIV.setImageResource(R.mipmap.a1);
            pIV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIV.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIV);
            pointlayout.addView(pIV);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);
    }

    private void initPV() {
        /*創建Fragment到VP數據源當中*/
        for (int i=0;i < cityList.size();i++){
            CityWeatherFragment cwWFragment = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwWFragment.setArguments(bundle);
            fragmentList.add(cwWFragment);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.main_iv_add:
                intent.setClass(this,CityManagerActivity.class);
                break;

            case R.id.main_iv_more:
                break;
        }

    }
}
