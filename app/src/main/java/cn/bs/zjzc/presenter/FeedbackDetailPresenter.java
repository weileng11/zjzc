package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IFeedbackDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.FeedbackDetailModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IFeedbackDetailView;

/**
 * Created by Ming on 2016/6/14.
 */
public class FeedbackDetailPresenter {

    private IFeedbackDetailView mFeedbackDetailView;
    private IFeedbackDetailModel mFeedbackDetailModel;

    public FeedbackDetailPresenter(IFeedbackDetailView feedbackDetailView) {
        mFeedbackDetailView = feedbackDetailView;
        mFeedbackDetailModel = new FeedbackDetailModel();
    }

    public void deleteFeedbackItem(String id) {
        mFeedbackDetailModel.DeleteFeedbackItem(id, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mFeedbackDetailView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {

            }
        });
    }
}
