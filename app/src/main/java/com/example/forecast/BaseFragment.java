package com.example.forecast;
/*Xuitils爬網步驟
1.聲明整體模塊
2.執行網路請求操作
3.實現方法依需求回調
*/
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {

    public void loadData(String path) {
        //網路請求
        RequestParams parms = new RequestParams(path);
        x.http().get(parms,this);

    }
    //獲取成功時，回調的介面
    @Override
    public void onSuccess(String result) {

    }
    //獲取失敗時，回調的介面
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }
    //取消請求時，回調的介面
    @Override
    public void onCancelled(CancelledException cex) {

    }
    //取消完成後，回調的介面
    @Override
    public void onFinished() {

    }
}
