package cn.bs.zjzc.model;

import java.util.List;
import java.util.Map;

import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.NearByRadarResponse;
import cn.bs.zjzc.model.response.OrderRespose;
import cn.bs.zjzc.model.response.ServicePhone;
import cn.bs.zjzc.model.response.UserDataResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IHomeModel {

    void getUserData(HttpTaskCallback<UserDataResponse.DataBean> httpTaskCallback);

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
    void requestCarreyMoney(String type, String city_id, String take_x, String take_y, String receipt_x, String receipt_y, HttpTaskCallback<CarryMoneyResponse.DataBean> httpTaskCallback);

    /**
     * 首页下单
     *
     * @param photoMap
     * @param orderParameters
     * @param httpTaskCallback
     */
    void requestOrder(Map<String, UploadFileBody> photoMap, OrderParameters orderParameters, HttpTaskCallback<OrderRespose.DataBean> httpTaskCallback);

    /**
     * 请求获取省份和城市列表
     */
    void requestAllProvinceAndCity(HttpTaskCallback<List<ProvinceCityListResponse.DataBean>> httpTaskCallback);

    /**
     * 获取版本更新
     */
    void getAppVersionInfo(HttpTaskCallback<AppVersionInfo> versionInfoHttpTaskCallback);

    /**
     * 获取客服电话
     */
    void getServicePhone(HttpTaskCallback<ServicePhone> servicePhone);

    /**
     * 首页周边雷达
     *
     * @param type       类型（1 接单界面，2 下单页面）
     * @param order_type 订单类型（1 外卖，2 代买，3 代办，4 车友，5 速递）【注：不传就返回全部】
     * @param x
     * @param y
     * @param callback
     */
    void getNearByRadar(int type, String order_type, String x, String y, HttpTaskCallback<NearByRadarResponse> callback);
}
