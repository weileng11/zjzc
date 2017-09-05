package cn.bs.zjzc.model.impl;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.ICancelOrderModel;
import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.IUserModel;
import cn.bs.zjzc.model.bean.User;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/3.
 */
public class CancelOrderModel implements ICancelOrderModel {

    @Override
    public void cancelOrder(String type, String order_id, String reason, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.cancelOrder);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("order_id", order_id)
                .addParams("reason", reason)
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
