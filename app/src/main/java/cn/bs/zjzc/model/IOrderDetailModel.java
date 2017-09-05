package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderSetting;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface IOrderDetailModel extends IBaseModel {

    void getOrderDetail(String orderType, String order_id, HttpTaskCallback<OrderDetail> callback);

    void confirmTakeGoods(String orderType, String order_id, HttpTaskCallback<BaseResponse> httpTaskCallback);
}
