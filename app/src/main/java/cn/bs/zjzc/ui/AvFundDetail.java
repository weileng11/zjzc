package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.FundetailAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.presenter.FundDetailPresenter;
import cn.bs.zjzc.ui.view.IFundDetailView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvFundDetail extends BaseActivity implements IFundDetailView {
    private Context mContext = this;
    private FundDetailPresenter mFundDetailPresenter;
    private ListView lvDetailList;
    private TopBarView topBar;
    private FundetailAdapter adapter;

    private int page = 1;
    private PtrClassicFrameLayout mPtrFrame;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_load_list);
        initViews();
        initBasic();
        mFundDetailPresenter = new FundDetailPresenter(this);
        mFundDetailPresenter.getFundDetail(page + "");
    }

    private void initBasic() {
        adapter = new FundetailAdapter(mContext);
        lvDetailList.setAdapter(adapter);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mFundDetailPresenter.getFundDetail(page + "");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mFundDetailPresenter.getFundDetail(page + "");
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.top_bar));
        mPtrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptrclassicFrameLayout));
        lvDetailList = ((ListView) findViewById(R.id.lv_list));

        topBar.setTitle("提现明细");
    }


    @Override
    public void showFundDetail(FundDetailResponse.DataBean data) {
        if (page == 1) {
            adapter.setData(data.list);
        } else {
            if (page < Integer.parseInt(data.page.page_count))
                adapter.addDatas(data.list);
        }
        mPtrFrame.refreshComplete();
        page++;
    }

    @Override
    public void completeFresh() {
        mPtrFrame.refreshComplete();
    }
}
