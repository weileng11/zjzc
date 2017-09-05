package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IRechargeModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.RechargeResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargeModel implements IRechargeModel {

    @Override
    public void recharge(String type, String money, final HttpTaskCallback<RechargeResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.recharge);
        PostHttpTask<RechargeResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("money", money)
                .execute(new GsonCallback<RechargeResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(RechargeResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });

    }
}
