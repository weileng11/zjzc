package cn.bs.zjzc.presenter;

import java.util.List;
import java.util.Map;

import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.model.IHomeModel;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.HomeModel;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.NearByRadarResponse;
import cn.bs.zjzc.model.response.OrderRespose;
import cn.bs.zjzc.model.response.ServicePhone;
import cn.bs.zjzc.ui.view.IHomeView;
import cn.bs.zjzc.util.SPUtils;

/**
 * Created by Ming on 2016/6/20.
 */
public class HomePresenter {
    private IHomeView view;
    private IHomeModel model;

    public HomePresenter(IHomeView view) {
        this.view = view;
        model = new HomeModel();
    }

    /**
     * 获取配送距离和运费
     * mgc
     *
     * @param type      订单类型
     * @param city_id   城市id
     * @param take_x    取货地址坐标
     * @param take_y
     * @param receipt_x 收货地址坐标
     * @param receipt_y
     */
    public void getCarreyMoney(String type, String city_id, String take_x, String take_y, String receipt_x, String receipt_y) {
//        view.showLoading();
        model.requestCarreyMoney(type, city_id, take_x, take_y, receipt_x, receipt_y, new HttpTaskCallback<CarryMoneyResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(CarryMoneyResponse.DataBean data) {
                view.hideLoading();
                view.updateCarryMoneyView(data);
            }
        });
    }

    /**
     * 首页下单
     *
     * @param photoMap
     * @param orderParameters
     */
    public void order(Map<String, UploadFileBody> photoMap, OrderParameters orderParameters) {
        view.showLoading();
        model.requestOrder(photoMap, orderParameters, new HttpTaskCallback<OrderRespose.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(OrderRespose.DataBean data) {
                view.hideLoading();
                view.orderSuccessView(data);
            }
        });
    }

    /**
     * 获取省份和城市列表
     */
    public void getProvinceAndCity() {
        view.showLoading();
        model.requestAllProvinceAndCity(new HttpTaskCallback<List<ProvinceCityListResponse.DataBean>>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(List<ProvinceCityListResponse.DataBean> data) {
                view.hideLoading();
                view.getProvinceAndCitySuccess(data);
            }
        });
    }

    /**
     * 获取版本更新信息
     */
    public void getAppVersionInfo() {
        model.getAppVersionInfo(new HttpTaskCallback<AppVersionInfo>() {
            @Override
            public void onTaskFailed(String errorInfo) {
            }

            @Override
            public void onTaskSuccess(AppVersionInfo data) {
                view.getVersionInfoSuccess(data);
            }
        });
    }

    /**
     * 获取客服电话
     */
    public void getServicePhone() {
        model.getServicePhone(new HttpTaskCallback<ServicePhone>() {
            @Override
            public void onTaskFailed(String errorInfo) {
            }

            @Override
            public void onTaskSuccess(ServicePhone data) {
                SPUtils.put(view.getContext(), "servicePhone", data.data.phone);
            }
        });
    }

    /**
     * 获取首页地图上，附近接单，下单用户的 锚点
     *
     * @param type 类型（1 接单界面，2 下单页面）
     * @param order_type 订单类型（1 外卖，2 代买，3 代办，4 车友，5 速递）【注：不传就返回全部】
     * @param x
     * @param y
     */
    public void getNearByRadar(int type, String order_type, String x, String y) {
        model.getNearByRadar(type, order_type, x, y, new HttpTaskCallback<NearByRadarResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(NearByRadarResponse data) {
                view.getNearByRadarView(data);
            }
        });
    }
}
