package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.FeedbackListAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.FeedbackListResponse;
import cn.bs.zjzc.presenter.FeedbackListPresenter;
import cn.bs.zjzc.ui.view.IFeedbackListView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvFeedbackList extends BaseActivity implements IFeedbackListView {
    private Context mContext = this;
    private FeedbackListPresenter mFeedbackListPresenter;
    private TopBarView topBar;
    private ListView lvFeedbackList;
    private FeedbackListAdapter feedbackListAdapter;
    private PtrClassicFrameLayout mPtrFrame;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_feedback_list);
        initViews();
        initEvents();
        initBasic();
        mFeedbackListPresenter = new FeedbackListPresenter(this);
    }

    private void initBasic() {
        feedbackListAdapter = new FeedbackListAdapter(mContext);
        lvFeedbackList.setAdapter(feedbackListAdapter);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mFeedbackListPresenter.getFeedbackList(page + "", "8");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mFeedbackListPresenter.getFeedbackList(page + "", "8");
            }
        });
    }

    private void initEvents() {
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AvFeedback.class));
            }
        });

        lvFeedbackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, AvFeedbackDetail.class);
                intent.putExtra("feedback_detail", feedbackListAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.feedback_list_top_bar));
        lvFeedbackList = ((ListView) findViewById(R.id.feedback_list));
        mPtrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptrClassicFrameLayout));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        page = 1;
        mFeedbackListPresenter.getFeedbackList(page + "", "8");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFeedbackListPresenter.getFeedbackList(page + "", "8");
    }

    @Override
    public void showFeedbackList(List<FeedbackListResponse.DataBean> datas) {
        if (page == 1) {
            feedbackListAdapter.getData().clear();
            feedbackListAdapter.setData(datas);
        } else {
            if (datas == null) {
                mPtrFrame.refreshComplete();
                return;
            }
            feedbackListAdapter.getData().addAll(datas);
        }
        feedbackListAdapter.notifyDataSetChanged();
        page++;
        mPtrFrame.refreshComplete();
    }
}