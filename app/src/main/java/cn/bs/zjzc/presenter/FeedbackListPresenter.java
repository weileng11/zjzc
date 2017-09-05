package cn.bs.zjzc.presenter;

import java.util.List;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IFeedbackListModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.FeedbackListModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.FeedbackListResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IFeedbackListView;

/**
 * Created by Ming on 2016/6/14.
 */
public class FeedbackListPresenter {
    private IFeedbackListView mFeedbackListView;
    private IFeedbackListModel mFeedbackListModel;

    public FeedbackListPresenter(IFeedbackListView feedbackView) {
        mFeedbackListView = feedbackView;
        mFeedbackListModel = new FeedbackListModel();
    }

    public void getFeedbackList(String page, String number) {
        mFeedbackListModel.getFeedbackList(page, number, new HttpTaskCallback<List<FeedbackListResponse.DataBean>>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mFeedbackListView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(List<FeedbackListResponse.DataBean> datas) {
                mFeedbackListView.showFeedbackList(datas);
            }
        });
    }
}
