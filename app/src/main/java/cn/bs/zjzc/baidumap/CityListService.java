package cn.bs.zjzc.baidumap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cn.bs.zjzc.App;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.L;

public class CityListService extends Service {

    public CityListService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestProvinceCityList();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 请求获取省份和城市列表
     */
    public void requestProvinceCityList() {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getProvinceCityList);
        PostHttpTask<ProvinceCityListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<ProvinceCityListResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        System.out.println(errorInfo);
                    }
                    @Override
                    public void onSuccess(ProvinceCityListResponse response) {
                        App.LOGIN_USER.setProvinceCityList(response.data);
                        L.d("MGC",response.data.toString());
                    }
                });
    }
}
