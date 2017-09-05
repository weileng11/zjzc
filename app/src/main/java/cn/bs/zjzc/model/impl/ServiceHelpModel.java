package cn.bs.zjzc.model.impl;

import java.net.URL;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IServiceHelpModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FAQListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/7/7.
 */
public class ServiceHelpModel implements IServiceHelpModel {
    @Override
    public void getFAQList(String keyword, String page, final HttpTaskCallback<FAQListResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getFAQList);
        PostHttpTask<FAQListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("keyword", keyword)
                .addParams("page", page)
                .execute(new GsonCallback<FAQListResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(FAQListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
