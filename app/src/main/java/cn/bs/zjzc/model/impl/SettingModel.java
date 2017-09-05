package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.ISettingModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/8.
 */
public class SettingModel implements ISettingModel {


    @Override
    public void logout(final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.logoutPath);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.logout();
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void getAppVersionInfo(final HttpTaskCallback<AppVersionInfo> versionInfoHttpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.apkUpdate);
        PostHttpTask<AppVersionInfo> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<AppVersionInfo>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        versionInfoHttpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(AppVersionInfo response) {
                        versionInfoHttpTaskCallback.onTaskSuccess(response);
                    }
                });
    }
}
