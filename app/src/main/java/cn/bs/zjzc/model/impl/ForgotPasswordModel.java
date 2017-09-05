package cn.bs.zjzc.model.impl;


import android.text.TextUtils;

import cn.bs.zjzc.model.IForgotPasswordModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IForgotPasswordView;
import cn.bs.zjzc.util.DensityUtils;

/**
 * Created by Ming on 2016/6/12.
 */
public class ForgotPasswordModel implements IForgotPasswordModel {

    @Override
    public void recoverPassword(String phone, String code, String passwd, String rePasswd, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.forgetPassword);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("code", code)
                .addParams("phone", phone)
                .addParams("passwd", passwd)
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });

    }

    @Override
    public void getCode(String phone, String type, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getCheckCodePath);
        //获取验证码请求
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("type", type)
                .addParams("phone", phone)
                .addParams("token", "")
                .addParams("order_id", "")
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
