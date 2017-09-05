package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOrderSettingModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderSetting;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.L;

/**
 * Created by Administrator on 2016/6/22.
 */
public class OrderSettingModel implements IOrderSettingModel {

    /**
     * 获取接单设置
     */
    @Override
    public void getSetting(final HttpTaskCallback<OrderSetting> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getOderSetting);
        PostHttpTask<OrderSetting> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<OrderSetting>() {
                    @Override
                    public void onFailed(String info) {
                        callback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(OrderSetting response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

    /**
     * 保存接单设置
     */
    @Override
    public void saveSetting(String orderType, String range, String isFlush, String acceptType,
                            String isPush, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.oderSetting);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("order", orderType)
                .addParams("distance", range)
                .addParams("flush", isFlush)
                .addParams("type", acceptType)
                .addParams("push", isPush)
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
