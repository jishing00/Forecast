package com.example.forecast;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forecast.Bean.WeatherBean;
import com.example.forecast.DB.DBManager;
import com.google.gson.Gson;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener {

    TextView carTV,coldTV,clothTV,sportTV,UVTV,
             tempTV,windTV,conTV,tempRangeTV,cityTV,dateTV;
    ImageView dayIV;
    LinearLayout future;
    String url1="https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-089?Authorization=CWB-C2D1E111-ADFC-4425-9BDD-AAB2DB4E666D&limit=5&format=JSON&locationName=";
    String url2="&sort=time";
    String city;
    private List<WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean> weatherElementBeanList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        // Inflate the layout for this fragment
        //通過activity傳值到當前fragment
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = url1+city+url2;
        //調用BF父類方法
        loadData(url);
        initView(view);
        return view;
    }

    @Override
    public void onSuccess(String result) {

        ParseShowData(result);

        int i = DBManager.updateInfoByCity(city, result);//資料庫(已變動)筆數
        if(i<= 0){
            //小於等於0，代表更新失敗資料庫目前，無此縣市 - > 手動加入
            DBManager.addCityInfo(city,result);
        }
    }



    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

        String s = DBManager.queryInfoByCity(city);
        if (!TextUtils.isEmpty(s)) {
            ParseShowData(s);
        }
    }
    //用Gson解析資料
    private void ParseShowData(String result) {
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.RecordsBean.LocationsBean locationsBean = weatherBean.getRecords().getLocations().get(0);

        weatherElementBeanList =  locationsBean.getLocation().get(0).getWeatherElement();

        dateTV.setText(locationsBean.getLocation().get(0).getWeatherElement().get(0).getTime().get(0).getStartTime());
        cityTV.setText(locationsBean.getLocation().get(0).getLocationName());

        //氣象指數wx近況
        WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean weatherElementBean = locationsBean.getLocation().get(0).getWeatherElement().get(1);

        conTV.setText(weatherElementBean.getTime().get(0).getElementValue().get(0).getValue());
        tempTV.setText(locationsBean.getLocation().get(0).getWeatherElement().get(3).getTime().get(0).getElementValue().get(0).getValue());
        windTV.setText(locationsBean.getLocation().get(0).getWeatherElement().get(8).getTime().get(0).getElementValue().get(0).getValue());
        tempRangeTV.setText(locationsBean.getLocation().get(0).getWeatherElement().get(1).getTime().get(0).getElementValue().get(0).getValue());

        List<WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean.TimeBean> futurelist = locationsBean.getLocation().get(0).getWeatherElement().get(1).getTime();
        futurelist.remove(0);

        for (int i = 0; i < 4; i++) {


            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            future.addView(itemView);

            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_tv_iv);

            WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean.TimeBean weatherDataBean = futurelist.get(i);

            idateTv.setText(weatherDataBean.getStartTime());
            iconTv.setText(weatherDataBean.getElementValue().get(i).getValue());
            //itemprangeTv.setText(weatherDataBean.getTime().get(0).getElementValue().get(0).getValue());

        }
    }
    public void initView(View view){
        carTV = view.findViewById(R.id.frag_indexWash_tv);
        coldTV = view.findViewById(R.id.frag_indexCold_tv);
        clothTV = view.findViewById(R.id.frag_indexDress_tv);
        sportTV = view.findViewById(R.id.frag_indexSport_tv);
        UVTV = view.findViewById(R.id.frag_indexUV_tv);
        tempTV = view.findViewById(R.id.frag_currtemp_tv);
        windTV = view.findViewById(R.id.frag_wind_tv);
        conTV = view.findViewById(R.id.frag_condition_tv);
        tempRangeTV = view.findViewById(R.id.frag_tempRange_tv);
        cityTV = view.findViewById(R.id.frag_city_tv);
        dateTV = view.findViewById(R.id.frag_date_tv);
        dayIV = view.findViewById(R.id.frag_today_tv);
        future = view.findViewById(R.id.frag_center_layout);

        //設置監聽器事件
        carTV.setOnClickListener(this);
        coldTV.setOnClickListener(this);
        clothTV.setOnClickListener(this);
        sportTV.setOnClickListener(this);
        UVTV.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (v.getId()){
            case R.id.frag_indexDress_tv:
                builder.setTitle("穿衣指數");
                WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean weatherElementBean = weatherElementBeanList.get(1);
                String msg = weatherElementBean.getTime().get(0).getElementValue().get(0).getValue();
                builder.setMessage(msg);
                builder.setPositiveButton("確定",null);
                break;

            case R.id.frag_indexCold_tv:
                builder.setTitle("感冒指數");
                WeatherBean.RecordsBean.LocationsBean.LocationBean.WeatherElementBean weatherElementBean2 = weatherElementBeanList.get(2);
                msg = weatherElementBean2.getTime().get(0).getElementValue().get(0).getValue();
                builder.setMessage(msg);
                builder.setPositiveButton("確定",null);
                break;

            case R.id.frag_indexWash_tv:

                break;

            case R.id.frag_indexSport_tv:

                break;

            case R.id.frag_indexUV_tv:

                break;
        }
    }
}
