package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.bean.OrderListRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.OrderListResponse;

/**
 * Created by Ming on 2016/6/29.
 */
public interface IMyOrderModel extends IBaseModel{
    void getOrderList(OrderListRequestBody requestBody, HttpTaskCallback<OrderListResponse.DataBean> callback);
}
