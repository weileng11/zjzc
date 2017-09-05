package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IAddAccountModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/15.
 */
public class AddAccountModel implements IAddAccountModel {
    @Override
    public void addAccount(BankListResponse.DataBean bankData, String account, String name, final HttpTaskCallback<BankAccountInfoResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.addBackAccount);
        String type = "1";
        if (bankData.name.equals("支付宝")) {
            type = "2";
        }
        PostHttpTask<BankAccountInfoResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("rank_id", bankData.id)
                .addParams("acount", account)
                .addParams("name", name)
                .execute(new GsonCallback<BankAccountInfoResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BankAccountInfoResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
