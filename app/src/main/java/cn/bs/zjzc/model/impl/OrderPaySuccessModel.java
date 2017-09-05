package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOrderPaySuccessModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by mgc on 2016/7/6.
 */
public class OrderPaySuccessModel implements IOrderPaySuccessModel {


    @Override
    public void getWalletBalance(final HttpTaskCallback<BalanceResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.balanceInfo);
        PostHttpTask<BalanceResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<BalanceResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BalanceResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void getOrderDetail(String type, String order_id, final HttpTaskCallback<OrderDetail> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderDetail);
        PostHttpTask<OrderDetail> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
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
    public void createPayment(String pay_type, String order_id, String is_system, String tip_money, final HttpTaskCallback<OrderPayResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.addTipMoney);
        PostHttpTask<OrderPayResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("pay_type", pay_type)
                .addParams("order_id", order_id)
                .addParams("is_system", is_system)
                .addParams("tip_money", tip_money)
                .execute(new GsonCallback<OrderPayResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderPayResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

}
