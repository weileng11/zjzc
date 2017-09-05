package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IFundDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/7/6.
 */
public class FundDetailModel implements IFundDetailModel {

    @Override
    public void getFundDetail(String p, final HttpTaskCallback<FundDetailResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.fundDetail);
        PostHttpTask<FundDetailResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", p)
                .execute(new GsonCallback<FundDetailResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(FundDetailResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
