package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.presenter.FeedbackPresenter;
import cn.bs.zjzc.ui.view.IFeedbackView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvFeedback extends BaseActivity implements IFeedbackView {
    private FeedbackPresenter mFeedbackPresenter;
    private TopBarView topBar;
    private EditText etContent;
    private TextView btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_feedback);
        mFeedbackPresenter = new FeedbackPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedbackPresenter.sendFeedback(etContent.getText().toString());
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.feedback_top_bar));
        etContent = ((EditText) findViewById(R.id.feedback_et_content));
        btnConfirm = ((TextView) findViewById(R.id.feedback_confirm));
    }
}
