package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderPayResponse;

/**
 * Created by mgc on 2016/7/6.
 */
public interface IOrderPaySuccessModel extends IBaseModel {

    /**
     * 获取账户余额
     */
    void getWalletBalance(HttpTaskCallback<BalanceResponse> callback);

    /**
     * 获取订单详情
     * @param type 类型（1 我的接单，2 我的订单）
     * @param order_id
     */
    void getOrderDetail(String type, String order_id,HttpTaskCallback<OrderDetail> callback);

    /**
     * /**
     * 后台生成支付订单
     *
     * @param pay_type      支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
     * @param order_id
     * @param is_system     是否为系统自动添加（1 是，2 不是）
     * @param tip_money     小费金额
     * @param callback
     */
    void createPayment(String pay_type, String order_id, String is_system,String tip_money, HttpTaskCallback<OrderPayResponse> callback);
}
