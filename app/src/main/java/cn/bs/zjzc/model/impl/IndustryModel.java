package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IIndustryModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.IndustryResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/13.
 */
public class IndustryModel implements IIndustryModel {

    @Override
    public void getIndustryList(final HttpTaskCallback<IndustryResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getIndustry);
        PostHttpTask<IndustryResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", "2")
                .execute(new GsonCallback<IndustryResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(IndustryResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void changeIndustry(final String industry, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeUserInfo);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("industry", industry)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.setIndustry(industry);
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
