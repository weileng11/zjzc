package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.WithdrawDetailAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;
import cn.bs.zjzc.presenter.WithdrawDetailPresenter;
import cn.bs.zjzc.ui.view.IWithdrawDetailView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/6/16.
 */
public class AvWithdrawDetail extends BaseActivity implements IWithdrawDetailView {

    private Context mContext = this;
    private WithdrawDetailPresenter mWithdrawDetailPresenter;
    private ListView lvDetailList;
    private TopBarView topBar;
    private WithdrawDetailAdapter adapter;

    private int page = 1;
    private PtrClassicFrameLayout mPtrFrame;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_load_list);
        initViews();
        initBasic();
        mWithdrawDetailPresenter = new WithdrawDetailPresenter(this);
        mWithdrawDetailPresenter.getWithdrawDetail(page + "");
    }

    private void initBasic() {
        adapter = new WithdrawDetailAdapter(mContext);
        lvDetailList.setAdapter(adapter);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mWithdrawDetailPresenter.getWithdrawDetail(page + "");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mWithdrawDetailPresenter.getWithdrawDetail(page + "");
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
    public void showWithdrawDetail(WithdrawDetailResponse.DataBean data) {
        if (page == 1) {
            adapter.setData(data.list);
        } else {
            adapter.addDatas(data.list);
        }
        mPtrFrame.refreshComplete();
        page++;
    }
}
