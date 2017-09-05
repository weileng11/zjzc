package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderSetting;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface IOrderSettingModel extends IBaseModel {

    void getSetting(HttpTaskCallback<OrderSetting> callback );

    void saveSetting(String orderType, String range, String isFlush, String acceptType, String isPush, HttpTaskCallback<BaseResponse> callback);
}
