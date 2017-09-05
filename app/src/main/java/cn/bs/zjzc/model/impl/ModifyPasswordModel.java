package cn.bs.zjzc.model.impl;


import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IModifyPasswordModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/12.
 */
public class ModifyPasswordModel implements IModifyPasswordModel {
    @Override
    public void modifyPassword(String org_passwd, String passwd, String repasswd, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeLoginPassword);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("org_passwd", org_passwd)
                .addParams("passwd", passwd)
                .addParams("repasswd", repasswd)
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
