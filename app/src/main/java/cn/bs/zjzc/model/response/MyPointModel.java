package cn.bs.zjzc.model.response;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IMyPointModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/7/11.
 */
public class MyPointModel implements IMyPointModel {
    @Override
    public void getPointList(String page, final HttpTaskCallback<PointDetailResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getPointList);
        PostHttpTask<PointDetailResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", page)
                .execute(new GsonCallback<PointDetailResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(PointDetailResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
