package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IMyWalletModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.MyWalletResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/14.
 */
public class MyWalletModel implements IMyWalletModel {

    @Override
    public void getWalletInfo(final HttpTaskCallback<MyWalletResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.walletIndexInfo);
        PostHttpTask<MyWalletResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<MyWalletResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(MyWalletResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
