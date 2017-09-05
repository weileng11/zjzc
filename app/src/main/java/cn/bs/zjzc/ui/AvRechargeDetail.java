package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.RechargeDetailAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.RechargeDetailResponse;
import cn.bs.zjzc.presenter.RechargeDetailPresenter;
import cn.bs.zjzc.ui.view.IRechargeDetailView;
import cn.bs.zjzc.util.L;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/6/16.
 */
public class AvRechargeDetail extends BaseActivity implements IRechargeDetailView {
    private Context mContext = this;
    private RechargeDetailPresenter mRechargeDetailPresenter;
    private ListView lvDetailList;
    private TopBarView topBar;
    private RechargeDetailAdapter adapter;
    private PtrClassicFrameLayout mPtrFrame;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_load_list);
        initViews();
        initBasic();
        mRechargeDetailPresenter = new RechargeDetailPresenter(this);
        mRechargeDetailPresenter.getRechargeDetail(page + "");
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.top_bar));
        mPtrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptrclassicFrameLayout));
        lvDetailList = ((ListView) findViewById(R.id.lv_list));

        topBar.setTitle("充值明细");
    }

    private void initBasic() {
        adapter = new RechargeDetailAdapter(mContext);
        lvDetailList.setAdapter(adapter);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                L.e("==================" + page + "");
                mRechargeDetailPresenter.getRechargeDetail(page + "");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mRechargeDetailPresenter.getRechargeDetail(page + "");
            }
        });
    }


    @Override
    public void showRechargeDetail(RechargeDetailResponse.DataBean data) {
        if (page == 1) {
            adapter.setData(data.list);
        } else {
            adapter.addDatas(data.list);
        }
        mPtrFrame.refreshComplete();
        page++;
    }

}
