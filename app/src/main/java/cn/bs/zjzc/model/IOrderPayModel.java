package cn.bs.zjzc.model;

import java.util.List;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.OrderAmountResponse;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.model.response.OrderPayResponse;

/**
 * Created by mgc on 2016/7/1.
 */
public interface IOrderPayModel extends IBaseModel {

    /**
     * 获取订单总费用--支付前
     *
     * @param order_id
     * @param coupon_id        优惠券
     * @param httpTaskCallback
     */
    void getOrderAmount(String order_id, String coupon_id, HttpTaskCallback<OrderAmountResponse.DataBean> httpTaskCallback);

    /**
     * 获取订单可用优惠券
     *
     * @param city_id
     * @param httpTaskCallback
     */
    void getOrderCoupon(String city_id, HttpTaskCallback<List<OrderCouponResponse.DataBean>> httpTaskCallback);

    /**
     * /**
     * 后台生成支付订单
     *
     * @param type      支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
     * @param order_id
     * @param coupon_id
     * @param callback
     */
    void createPayment(String type, String order_id, String coupon_id, HttpTaskCallback<OrderPayResponse> callback);
}
