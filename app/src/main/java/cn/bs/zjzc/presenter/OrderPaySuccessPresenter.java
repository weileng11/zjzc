package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IOrderPaySuccessModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OrderPaySuccessModel;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.ui.view.IAvOrderPaySuccessView;

/**
 * Created by mgc on 2016/7/6.
 */
public class OrderPaySuccessPresenter {
    private IAvOrderPaySuccessView view;
    private IOrderPaySuccessModel model;

    public OrderPaySuccessPresenter(IAvOrderPaySuccessView view) {
        this.view = view;
        model = new OrderPaySuccessModel();
    }

    /**
     * 获取账户余额
     */
    public void getWalletBalance() {
        view.showLoading();
        model.getWalletBalance(new HttpTaskCallback<BalanceResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BalanceResponse data) {
                view.hideLoading();
                view.getWalletBalanceView(data.data);
            }
        });
    }

    /**
     * 获取订单详情
     *
     * @param type
     * @param order_id
     */
    public void getOrderDetail(String type, String order_id) {
        view.showLoading();
        model.getOrderDetail(type, order_id, new HttpTaskCallback<OrderDetail>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(OrderDetail data) {
                view.hideLoading();
                view.getOrderDetailView(data);
            }
        });
    }

    /**
     * 生成小费支付订单
     *
     * @param pay_type
     * @param order_id
     * @param is_system
     * @param tip_money
     */
    public void createPayment(String pay_type, String order_id, String is_system, String tip_money) {
        view.showLoading();
        model.createPayment(pay_type, order_id, is_system, tip_money, new HttpTaskCallback<OrderPayResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(OrderPayResponse data) {
                view.hideLoading();
                view.createPaymentView(data);
            }
        });
    }
}
