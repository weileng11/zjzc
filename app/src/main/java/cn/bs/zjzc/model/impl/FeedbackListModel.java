package cn.bs.zjzc.model.impl;

import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IFeedbackListModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FeedbackListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/14.
 */
public class FeedbackListModel implements IFeedbackListModel {

    @Override
    public void getFeedbackList(String page, String number, final HttpTaskCallback<List<FeedbackListResponse.DataBean>> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.feebackList);
        PostHttpTask<FeedbackListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", page)
                .addParams("number", number)
                .execute(new GsonCallback<FeedbackListResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(FeedbackListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
