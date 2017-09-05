package cn.bs.zjzc.model.impl;

import java.util.List;
import java.util.Map;

import cn.bs.zjzc.App;
import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.model.IHomeModel;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.NearByRadarResponse;
import cn.bs.zjzc.model.response.OrderRespose;
import cn.bs.zjzc.model.response.ServicePhone;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/20.
 */
public class HomeModel implements IHomeModel {
    @Override
    public void getUserData(final HttpTaskCallback<UserDataResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getUserInfo);
        PostHttpTask<UserDataResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<UserDataResponse>() {
                    @Override
                    public void onFailed(String info) {
                        callback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(UserDataResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }

    /**
     * 获取配送距离和运费
     * mgc
     *
     * @param type             订单类型
     * @param city_id          城市id
     * @param take_x           取货地址坐标
     * @param take_y
     * @param receipt_x        收货地址坐标
     * @param receipt_y
     * @param httpTaskCallback
     */
    @Override
    public void requestCarreyMoney(String type, String city_id, String take_x, String take_y, String receipt_x, String receipt_y, final HttpTaskCallback<CarryMoneyResponse.DataBean> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getCarryMoney);
        PostHttpTask<CarryMoneyResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("city_id", city_id)
                .addParams("type", type)
                .addParams("take_x", take_x)
                .addParams("take_y", take_y)
                .addParams("receipt_x", receipt_x)
                .addParams("receipt_y", receipt_y)
                .execute(new GsonCallback<CarryMoneyResponse>() {
                    @Override
                    public void onFailed(String info) {
                        httpTaskCallback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(CarryMoneyResponse response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });
    }


    /**
     * 首页下单
     *
     * @param photoMap
     * @param orderParameters
     * @param httpTaskCallback
     */
    @Override
    public void requestOrder(Map<String, UploadFileBody> photoMap, OrderParameters orderParameters, final HttpTaskCallback<OrderRespose.DataBean> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.order);
        PostHttpTask<OrderRespose> httpTask = new PostHttpTask<>(url);
        if (photoMap != null) {
            httpTask.addFiles(photoMap);
        }
        httpTask.params(orderParameters)
                .execute(new GsonCallback<OrderRespose>() {
                    @Override
                    public void onFailed(String info) {
                        httpTaskCallback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(OrderRespose response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });

    }

    /**
     * 请求获取省份和城市列表
     */
    @Override
    public void requestAllProvinceAndCity(final HttpTaskCallback<List<ProvinceCityListResponse.DataBean>> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getProvinceCityList);
        PostHttpTask<ProvinceCityListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<ProvinceCityListResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        httpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(ProvinceCityListResponse response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });
    }

    /**
     * 获取版本更新信息
     */
    @Override
    public void getAppVersionInfo(final HttpTaskCallback<AppVersionInfo> versionInfoHttpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.apkUpdate);
        PostHttpTask<AppVersionInfo> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<AppVersionInfo>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        versionInfoHttpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(AppVersionInfo response) {
                        versionInfoHttpTaskCallback.onTaskSuccess(response);
                    }
                });
    }

    /**
     * 获取客服电话
     */
    @Override
    public void getServicePhone(final HttpTaskCallback<ServicePhone> servicePhone) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.servicePhone);
        PostHttpTask<ServicePhone> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<ServicePhone>() {
                    @Override
                    public void onFailed(String errorInfo) {
                    }

                    @Override
                    public void onSuccess(ServicePhone response) {
                        servicePhone.onTaskSuccess(response);
                    }
                });
    }

    /**
     * 首页周边雷达
     *
     * @param type       类型（1 接单界面，2 下单页面）
     * @param order_type 订单类型（1 外卖，2 代买，3 代办，4 车友，5 速递）【注：不传就返回全部】
     * @param x          当前x坐标
     * @param y          当前y坐标
     * @param callback
     */
    @Override
    public void getNearByRadar(int type, String order_type, String x, String y, final HttpTaskCallback<NearByRadarResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.nearbyRadar);
        PostHttpTask<NearByRadarResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type + "")
                .addParams("order_type", order_type)
                .addParams("x", x)
                .addParams("y", y)
                .execute(new GsonCallback<NearByRadarResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(NearByRadarResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

}
