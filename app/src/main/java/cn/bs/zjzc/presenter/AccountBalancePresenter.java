package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IAccountBalanceModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.AccountBalanceModel;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.ui.view.IAccountBalanceView;

/**
 * Created by Ming on 2016/6/15.
 */
public class AccountBalancePresenter {
    private IAccountBalanceView mAccountBalanceView;
    private IAccountBalanceModel mAccountBalanceModel;

    public AccountBalancePresenter(IAccountBalanceView accountBalanceView) {
        mAccountBalanceView = accountBalanceView;
        mAccountBalanceModel = new AccountBalanceModel();
    }

    public void getBalanceInfo() {
        mAccountBalanceView.showLoading();
        mAccountBalanceModel.getBalanceInfo(new HttpTaskCallback<BalanceResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mAccountBalanceView.showMsg(errorInfo);
                mAccountBalanceView.hideLoading();
            }

            @Override
            public void onTaskSuccess(BalanceResponse.DataBean data) {
                mAccountBalanceView.hideLoading();
                mAccountBalanceView.showBalanceInfo(data);
            }
        });
    }

    public void getBankAccountInfo() {
        mAccountBalanceView.showLoading("正在获取帐号信息");
        mAccountBalanceModel.getBankAccountInfo(new HttpTaskCallback<BankAccountInfoResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mAccountBalanceView.hideLoading();
                mAccountBalanceView.showMsg(errorInfo);
                mAccountBalanceView.startAddAccountActivity();
            }

            @Override
            public void onTaskSuccess(BankAccountInfoResponse.DataBean data) {
                mAccountBalanceView.hideLoading();
                mAccountBalanceView.startWithdrawActivity(data);
            }
        });
    }
}
