package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IWithdrawModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.WithdrawModel;
import cn.bs.zjzc.model.response.WithdrawResponse;
import cn.bs.zjzc.ui.view.IWithdrawView;

/**
 * Created by Ming on 2016/6/15.
 */
public class WithdrawPresenter {
    private IWithdrawView mWithdrawView;
    private IWithdrawModel mWithdrawModel;

    public WithdrawPresenter(IWithdrawView withdrawView) {
        mWithdrawView = withdrawView;
        mWithdrawModel = new WithdrawModel();
    }

    public void withdraw(String password, String money) {
        mWithdrawView.showLoading("正在处理...");
        mWithdrawModel.withdraw(password, money, new HttpTaskCallback<WithdrawResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mWithdrawView.hideLoading();
                mWithdrawView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(WithdrawResponse data) {
                mWithdrawView.hideLoading();
                mWithdrawView.showMsg(data.errinfo);
                mWithdrawView.finish();
            }
        });
    }
}
