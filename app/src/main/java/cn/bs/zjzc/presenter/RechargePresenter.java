package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IRechargeModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.RechargeModel;
import cn.bs.zjzc.model.response.RechargeResponse;
import cn.bs.zjzc.ui.view.IRechargeView;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargePresenter {
    private IRechargeView mRechargeView;
    private IRechargeModel mRechargeModel;

    public RechargePresenter(IRechargeView rechargeView) {
        mRechargeView = rechargeView;
        mRechargeModel = new RechargeModel();
    }

    public void recharge(String type, String money) {
        mRechargeModel.recharge(type, money, new HttpTaskCallback<RechargeResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {

            }

            @Override
            public void onTaskSuccess(RechargeResponse.DataBean data) {
                mRechargeView.createPayment(data);
            }
        });
    }
}
