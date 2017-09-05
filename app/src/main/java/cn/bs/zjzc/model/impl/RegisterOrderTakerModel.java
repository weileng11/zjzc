package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IRegisterOrderTakerModel;
import cn.bs.zjzc.model.bean.RegisterOrderTakerRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/14.
 */
public class RegisterOrderTakerModel implements IRegisterOrderTakerModel {

    @Override
    public void registerOrderTaker(RegisterOrderTakerRequestBody requestBody, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.applyOrderTaker);

        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("address", requestBody.address)
                .addParams("my_real_name", requestBody.my_real_name)
                .addParams("ID_card_number", requestBody.ID_card_number)
                .addFiles(requestBody.photoMap)
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
