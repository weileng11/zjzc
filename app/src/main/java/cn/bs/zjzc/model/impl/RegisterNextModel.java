package cn.bs.zjzc.model.impl;


import cn.bs.zjzc.model.IRegisterNextModel;
import cn.bs.zjzc.model.bean.RegisterRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FinishRegisterResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/7.
 */
public class RegisterNextModel implements IRegisterNextModel {

    @Override
    public void register(final RegisterRequestBody registerRequestBody, final HttpTaskCallback<FinishRegisterResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.regPath);
        //注册请求
        PostHttpTask<FinishRegisterResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", registerRequestBody.token)
                .addParams("acount", registerRequestBody.acount)
                .addParams("name", registerRequestBody.name)
                .addParams("passwd", registerRequestBody.passwd)
                .addParams("code", registerRequestBody.code)
                .execute(new GsonCallback<FinishRegisterResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(FinishRegisterResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
