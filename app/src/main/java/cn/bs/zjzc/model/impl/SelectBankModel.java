package cn.bs.zjzc.model.impl;

import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.ISelectBankModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/15.
 */
public class SelectBankModel implements ISelectBankModel {

    @Override
    public void getBankList(final HttpTaskCallback<List<BankListResponse.DataBean>> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getBankList);
        PostHttpTask<BankListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<BankListResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BankListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
