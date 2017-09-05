package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IRechargeDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.RechargeDetailResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargeDetailModel implements IRechargeDetailModel {

    @Override
    public void getRechargeDetail(String p, final HttpTaskCallback<RechargeDetailResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.rechargeDetail);
        PostHttpTask<RechargeDetailResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", p)
                .execute(new GsonCallback<RechargeDetailResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(RechargeDetailResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
