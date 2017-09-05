package cn.bs.zjzc.model.impl;

import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOrderPayModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.OrderAmountResponse;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by mgc on 2016/7/1.
 */
public class OrderPayModel implements IOrderPayModel {

    /**
     * 获取订单总费用--支付前
     *
     * @param order_id
     * @param coupon_id        优惠券
     * @param httpTaskCallback
     */
    @Override
    public void getOrderAmount(String order_id, String coupon_id, final HttpTaskCallback<OrderAmountResponse.DataBean> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getOrderAmount);
        final PostHttpTask<OrderAmountResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("order_id", order_id)
                .addParams("coupon_id", coupon_id)
                .execute(new GsonCallback<OrderAmountResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        httpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderAmountResponse response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });
    }

    /**
     * 订单可用优惠券
     *
     * @param city_id
     * @param httpTaskCallback
     */
    @Override
    public void getOrderCoupon(String city_id, final HttpTaskCallback<List<OrderCouponResponse.DataBean>> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderCoupon);
        final PostHttpTask<OrderCouponResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("city_id", city_id)
                .execute(new GsonCallback<OrderCouponResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        httpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderCouponResponse response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });
    }

    /**
     * @param type      支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
     * @param order_id
     * @param coupon_id
     * @param callback
     */
    @Override
    public void createPayment(String type, String order_id, String coupon_id, final HttpTaskCallback<OrderPayResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderPay);
        PostHttpTask<OrderPayResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("order_id", order_id)
                .addParams("coupon_id", coupon_id)
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
