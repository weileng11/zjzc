package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IWithdrawModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.WithdrawResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/15.
 */
public class WithdrawModel implements IWithdrawModel {

    @Override
    public void withdraw(String password, String money, final HttpTaskCallback<WithdrawResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.withdraw);
        PostHttpTask<WithdrawResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("passwd", password)
                .addParams("money", money)
                .execute(new GsonCallback<WithdrawResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(WithdrawResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
