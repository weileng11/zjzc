package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOrderDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Administrator on 2016/6/29.
 */
public class OderDetailModer implements IOrderDetailModel {

    @Override
    public void getOrderDetail(String orderType, String order_id, final HttpTaskCallback<OrderDetail> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderDetail);
        PostHttpTask<OrderDetail> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", orderType)
                .addParams("order_id", order_id)
                .execute(new GsonCallback<OrderDetail>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderDetail response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void confirmTakeGoods(String orderType, String order_id, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.confirmTakeGoods);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", orderType)
                .addParams("order_id", order_id)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
