package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.OrderAmountResponse;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.model.response.OrderPayResponse;

/**
 * Created by mgc on 2016/7/1.
 */
public interface IOrderPayView extends IBaseView {

    //成功获取订单总费用--支付前
    void getOrderAmountSuccessView(OrderAmountResponse.DataBean dataBean);

    //获取订单总费用失败--支付前
    void getOrderAmountFailedView();

    //成功获取订单可用优惠券
    void getOrderCouponSuccessView(List<OrderCouponResponse.DataBean> dataBean);

    //后台成功生成订单
    void createPaymentSuccssView(OrderPayResponse data);
}
