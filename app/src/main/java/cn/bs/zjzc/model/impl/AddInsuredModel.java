package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IAddInsuredModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AddInsuredResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * 添加保价费计算方式
 * Created by mgc on 2016/7/1.
 */
public class AddInsuredModel implements IAddInsuredModel{

    /**
     * 获取保价计算
     * @param httpTaskCallback
     */
    @Override
    public void getInsured_way(final HttpTaskCallback<AddInsuredResponse.DataBean> httpTaskCallback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getInsuredWay);
        PostHttpTask<AddInsuredResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<AddInsuredResponse>() {
                    @Override
                    public void onFailed(String info) {
                        httpTaskCallback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(AddInsuredResponse response) {
                        httpTaskCallback.onTaskSuccess(response.data);
                    }
                });
    }
}
