package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.NearByRadarResponse;
import cn.bs.zjzc.model.response.OrderRespose;

/**
 * Created by Ming on 2016/6/13.
 */
public interface IHomeView extends IBaseView {

    /**
     * 运费（配送费和距离)
     */
    void updateCarryMoneyView(CarryMoneyResponse.DataBean data);

    /**
     * 下单成功
     *
     * @param data
     */
    void orderSuccessView(OrderRespose.DataBean data);

    /**
     * 获取开通服务的省份和城市
     *
     * @param data
     */
    void getProvinceAndCitySuccess(List<ProvinceCityListResponse.DataBean> data);

    /**
     * 获取版本信息
     *
     * @param data
     */
    void getVersionInfoSuccess(AppVersionInfo data);

    /**
     * 周边雷达
     */
    void getNearByRadarView(NearByRadarResponse data);
}
