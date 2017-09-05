package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.model.IRegisterModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.VerifyCodeResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/4.
 */
public class RegisterModel implements IRegisterModel {

    @Override
    public void getCode(String phone, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getCheckCodePath);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", "")
                .addParams("phone", phone)
                .addParams("type", "1")
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

    @Override
    public void next(String account, String code, final HttpTaskCallback<VerifyCodeResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.regPath);
        PostHttpTask<VerifyCodeResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("acount", account)
                .addParams("code", code)
                .execute(new GsonCallback<VerifyCodeResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(VerifyCodeResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
