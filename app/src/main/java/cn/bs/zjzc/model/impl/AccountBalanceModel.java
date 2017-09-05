package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IAccountBalanceModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/20.
 */
public class AccountBalanceModel implements IAccountBalanceModel {

    @Override
    public void getBalanceInfo(final HttpTaskCallback<BalanceResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.balanceInfo);

        PostHttpTask<BalanceResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<BalanceResponse>() {
                    @Override
                    public void onFailed(String info) {
                        callback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(BalanceResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }

    @Override
    public void getBankAccountInfo(final HttpTaskCallback<BankAccountInfoResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getBankInfo);
        PostHttpTask<BankAccountInfoResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<BankAccountInfoResponse>() {
                    @Override
                    public void onFailed(String info) {
                        callback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(BankAccountInfoResponse response) {
                        callback.onTaskSuccess(response.data);
                    }

                });
    }
}
