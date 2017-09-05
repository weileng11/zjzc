package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IAddAccountModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.AddAccountModel;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IAddAccountView;

/**
 * Created by Ming on 2016/6/15.
 */
public class AddAccountPresenter {

    private IAddAccountView mAddAccountView;
    private IAddAccountModel mAddAccountModel;

    public AddAccountPresenter(IAddAccountView addAccountView) {
        mAddAccountView = addAccountView;
        mAddAccountModel = new AddAccountModel();
    }

    public void addAccount(BankListResponse.DataBean bankData, String account, String name) {
        mAddAccountView.showLoading();
        mAddAccountModel.addAccount(bankData, account, name, new HttpTaskCallback<BankAccountInfoResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mAddAccountView.hideLoading();
                mAddAccountView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BankAccountInfoResponse.DataBean data) {
                mAddAccountView.hideLoading();
                mAddAccountView.startWithdrawActivity(data);
            }
        });

    }
}
