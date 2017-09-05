package cn.bs.zjzc.presenter;

import java.util.List;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.INewsCenterModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.NewsCenterModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.NewsListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.INewsCenterView;

/**
 * Created by Ming on 2016/6/17.
 */
public class NewsCenterPresenter {
    private INewsCenterView mNewsCenterView;
    private INewsCenterModel mNewsCenterModel;

    public NewsCenterPresenter(INewsCenterView newsCenterView) {
        mNewsCenterView = newsCenterView;
        mNewsCenterModel = new NewsCenterModel();
    }

    public void getNewsList(String page, String number) {
        mNewsCenterModel.getNewsList(page, number, new HttpTaskCallback<NewsListResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mNewsCenterView.showMsg(errorInfo);
                mNewsCenterView.refreshFail();
            }

            @Override
            public void onTaskSuccess(NewsListResponse.DataBean data) {
                mNewsCenterView.showNewsList(data);
            }
        });
    }

    public void setNewsRead(String id) {
        mNewsCenterModel.setNewsRead(id, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mNewsCenterView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {

            }
        });
    }

    public void deleteNews(String id, final int position) {
        mNewsCenterModel.deleteNews(id, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mNewsCenterView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mNewsCenterView.deleteNews(position);
            }
        });
    }
}
