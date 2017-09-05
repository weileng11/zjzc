package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.IMyCouponModel;
import cn.bs.zjzc.model.response.MyCoupon;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MyCouponModel implements IMyCouponModel {
    @Override
    public void getCouponList(String type, String page, final HttpTaskCallback<MyCoupon> myCouponHttpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.couponList);
        PostHttpTask<MyCoupon> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("page", page)
                .execute(new GsonCallback<MyCoupon>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        myCouponHttpTaskCallback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(MyCoupon response) {
                        myCouponHttpTaskCallback.onTaskSuccess(response);
                    }
                });
    }
}
