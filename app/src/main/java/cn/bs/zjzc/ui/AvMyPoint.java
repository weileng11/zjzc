package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.PointDetailAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.PointDetailResponse;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.MyPointPresenter;
import cn.bs.zjzc.ui.view.IMyPointView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvMyPoint extends BaseActivity implements IMyPointView {
    private Context mContext = this;
    private MyPointPresenter mMyPointPresenter;
    private TopBarView topBar;
    private TextView tvPoint;
    private ListView lvPointList;
    private PtrClassicFrameLayout ptrFrame;
    private PointDetailAdapter pointDetailAdapter;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_user_point);
        mMyPointPresenter = new MyPointPresenter(this);
        initViews();
        initBasic();
        initEvents();
        initListView();
    }

    private void initBasic() {
        String point = getIntent().getStringExtra("point");
        if (!TextUtils.isEmpty(point))
            tvPoint.setText(point + "分");
    }

    private void initListView() {
        pointDetailAdapter = new PointDetailAdapter(mContext);
        lvPointList.setAdapter(pointDetailAdapter);
        mMyPointPresenter.getPointList(String.valueOf(page));
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mMyPointPresenter.getPointList(String.valueOf(page));
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mMyPointPresenter.getPointList(String.valueOf(page));
            }
        });
    }

    private void initEvents() {
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvCommdWebView.class);
                intent.putExtra("title", "积分规则");
                intent.putExtra("url", RequestUrl.getWebRequestPath(RequestUrl.WebPath.pointRule));
                startActivity(intent);
            }
        });


    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.top_bar));
        tvPoint = ((TextView) findViewById(R.id.tv_point));
        lvPointList = ((ListView) findViewById(R.id.lv_point_list));
        ptrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptr_frame));
    }

    @Override
    public void completeFresh() {
        ptrFrame.refreshComplete();
    }

    @Override
    public void showPointList(PointDetailResponse.DataBean data) {
        if (page == 1) {
            pointDetailAdapter.setData(data.list);
        } else {
            if (page < Integer.parseInt(data.page.page_count)) {
                pointDetailAdapter.addDatas(data.list);
            }
        }
        ptrFrame.refreshComplete();
        page++;
    }
}
