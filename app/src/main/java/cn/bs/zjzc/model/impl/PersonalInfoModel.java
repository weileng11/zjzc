package cn.bs.zjzc.model.impl;

import java.io.File;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IPersonalInfoModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/14.
 */
public class PersonalInfoModel implements IPersonalInfoModel {

    public PersonalInfoModel() {

    }

    @Override
    public void changeHeader(final File file, final HttpTaskCallback<BaseResponse> taskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeHeader);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addFile("head", file.getName(), file)
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String info) {
                        taskCallback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.setHead_img(file.getAbsolutePath());
                        taskCallback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void changeGender(final String gender, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeUserInfo);
        String sex = null;
        if (gender.equals("男")) {
            sex = "1";
        } else if (gender.equals("女")) {
            sex = "2";
        }

        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("sex", sex)
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.setSex(gender);
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void changeAge(final String age, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeUserInfo);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("age", age)
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.setAge(age);
                        callback.onTaskSuccess(response);
                    }
                });
    }

}
