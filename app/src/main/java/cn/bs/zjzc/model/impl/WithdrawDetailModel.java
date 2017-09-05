package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IWithdrawDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/16.
 */
public class WithdrawDetailModel implements IWithdrawDetailModel {

    @Override
    public void getWithdrawDetail(String p, final HttpTaskCallback<WithdrawDetailResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.withdrawDetail);
        PostHttpTask<WithdrawDetailResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", p)
                .execute(new GsonCallback<WithdrawDetailResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(WithdrawDetailResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
