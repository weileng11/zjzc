package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IMyEvaluationModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.EvaluationListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/7/5.
 */
public class MyEvaluationModel implements IMyEvaluationModel {
    @Override
    public void getEvaluationList(String type, String page, final HttpTaskCallback<EvaluationListResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getEvaluationList);
        PostHttpTask<EvaluationListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("page", page)
                .execute(new GsonCallback<EvaluationListResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(EvaluationListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
