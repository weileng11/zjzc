package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IFeedbackModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.FeedbackModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.ui.view.IFeedbackView;

/**
 * Created by Ming on 2016/6/13.
 */
public class FeedbackPresenter {

    private IFeedbackView mFeedbackView;
    private IFeedbackModel mFeedbackModel;

    public FeedbackPresenter(IFeedbackView feedbackView) {
        mFeedbackView = feedbackView;
        mFeedbackModel = new FeedbackModel();
    }

    public void sendFeedback(String content) {
        mFeedbackView.showLoading("正在提交反馈...");
        mFeedbackModel.sendFeedback(content, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mFeedbackView.hideLoading();
                mFeedbackView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mFeedbackView.hideLoading();
                mFeedbackView.finish();
            }
        });
    }
}
