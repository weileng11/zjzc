package cn.bs.zjzc.presenter;

import java.util.List;

import cn.bs.zjzc.model.IOrderPayModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OrderPayModel;
import cn.bs.zjzc.model.response.OrderAmountResponse;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.ui.view.IOrderPayView;

/**
 * Created by mgc on 2016/7/1.
 */
public class OrderPayPresenter {

    private IOrderPayView view;
    private IOrderPayModel model;

    public OrderPayPresenter(IOrderPayView view) {
        this.view = view;
        model = new OrderPayModel();
    }

    /**
     * 获取订单总费用--支付前
     *
     * @param order_id
     * @param coupon_id 优惠券
     */
    public void getOrderAmount(String order_id, String coupon_id) {
        view.showLoading();
        model.getOrderAmount(order_id, coupon_id, new HttpTaskCallback<OrderAmountResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
                view.getOrderAmountFailedView();
            }

            @Override
            public void onTaskSuccess(OrderAmountResponse.DataBean data) {
                view.hideLoading();
                view.getOrderAmountSuccessView(data);
            }
        });
    }

    /**
     * 获取订单可用优惠券
     *
     * @param city_id
     */
    public void getOrderCoupon(String city_id) {
        view.showLoading();
        model.getOrderCoupon(city_id, new HttpTaskCallback<List<OrderCouponResponse.DataBean>>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(List<OrderCouponResponse.DataBean> data) {
                view.hideLoading();
                view.getOrderCouponSuccessView(data);
            }
        });
    }

    /**
     * 生成订单并支付
     *
     * @param type
     * @param order_id
     * @param coupon_id
     */
    public void createPayment(String type, String order_id, String coupon_id) {
        view.showLoading();
        model.createPayment(type, order_id, coupon_id, new HttpTaskCallback<OrderPayResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(OrderPayResponse data) {
                view.hideLoading();
                view.createPaymentSuccssView(data);
            }
        });
    }
}
