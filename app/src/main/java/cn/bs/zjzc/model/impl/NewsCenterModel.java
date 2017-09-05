package cn.bs.zjzc.model.impl;

import com.zhy.http.okhttp.callback.StringCallback;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.INewsCenterModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.NewsListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/17.
 */
public class NewsCenterModel implements INewsCenterModel {

    @Override
    public void getNewsList(String page, String number, final HttpTaskCallback<NewsListResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.newsList);
        PostHttpTask<NewsListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("page", page)
                .addParams("number", number)
                .execute(new GsonCallback<NewsListResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(NewsListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }

    @Override
    public void setNewsRead(String id, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.setNewsRead);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("number", id)
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
    public void deleteNews(final String id, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.deleteNews);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("number", id)
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
