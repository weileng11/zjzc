package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.FeedbackListResponse;
import cn.bs.zjzc.presenter.FeedbackDetailPresenter;
import cn.bs.zjzc.ui.view.IFeedbackDetailView;
import cn.bs.zjzc.util.DensityUtils;

/**
 * Created by Ming on 2016/6/14.
 */
public class AvFeedbackDetail extends BaseActivity implements IFeedbackDetailView {

    private FeedbackDetailPresenter mFeedbackDetailPresenter;
    private TextView tvContent;
    private TextView tvDate;
    private TextView btnDelete;
    private FeedbackListResponse.DataBean feedbackDetail;
    private TextView tvReply;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_feedback_detail);
        mFeedbackDetailPresenter = new FeedbackDetailPresenter(this);
        feedbackDetail = (FeedbackListResponse.DataBean) getIntent().getSerializableExtra("feedback_detail");
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedbackDetailPresenter.deleteFeedbackItem(feedbackDetail.id);
                finish();
            }
        });
    }

    private void initViews() {
        tvDate = ((TextView) findViewById(R.id.feedback_detail_tv_date));
        tvContent = ((TextView) findViewById(R.id.feedback_detail_tv_feedback_content));
        tvReply = ((TextView) findViewById(R.id.feedback_detail_tv_reply));
        btnDelete = ((TextView) findViewById(R.id.feedback_detail_btn_delete));

        long mills = Long.parseLong(feedbackDetail.ctime);
        tvDate.setText(DensityUtils.parseDate(new Date(mills), "yyyy年MM月dd日"));
        tvContent.setText(feedbackDetail.content);
        tvReply.setText(feedbackDetail.reply);
    }
}
