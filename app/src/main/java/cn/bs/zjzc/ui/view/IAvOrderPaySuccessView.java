package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderPayResponse;

/**
 * Created by mgc on 2016/7/6.
 */
public interface IAvOrderPaySuccessView extends IBaseView{

    //获取账户余额
    void getWalletBalanceView(BalanceResponse.DataBean data);

    //获取订单信息
    void getOrderDetailView(OrderDetail data);

    //生成支付订单
    void createPaymentView(OrderPayResponse data);
}
