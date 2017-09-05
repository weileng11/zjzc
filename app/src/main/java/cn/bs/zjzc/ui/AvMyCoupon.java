package cn.bs.zjzc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.MyCouponAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.MyListView;
import cn.bs.zjzc.model.response.MyCoupon;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.MyCouponPresenter;
import cn.bs.zjzc.ui.view.IMyCouponView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvMyCoupon extends BaseActivity implements IMyCouponView, View.OnClickListener {
    private TextView my_coupon_use_rules;
    private MyListView mListView;
    private TextView buttomTag;
    private MyCouponPresenter mPresenter;
    private String mType = "1"; //优惠券类型（1 未过期，2 过期，默认为1）
    private int page = 1;
    private List<MyCoupon.DataBean.ListBean> list;
    private MyCouponAdapter mAdapter;
    private boolean isOutOfData = true;
    private PtrClassicFrameLayout mPtrFrame;
    private TextView emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_coupon);
        initViews();
        initEvent();
        initBasic();
    }

    private void initBasic() {
        mPresenter = new MyCouponPresenter(this);
        mAdapter = new MyCouponAdapter(this);
        mListView.setAdapter(mAdapter);
        mPresenter.getCouponList(mType, page + "");
    }

    private void initEvent() {
        my_coupon_use_rules.setOnClickListener(this);
        buttomTag.setOnClickListener(this);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                if (isOutOfData) {
                    mType = "1";
                } else {
                    mType = "2";
                }
                mPresenter.getCouponList(mType, page + "");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                if (isOutOfData) {
                    mType = "1";
                } else {
                    mType = "2";
                }
                mPresenter.getCouponList(mType, page + "");
            }
        });
    }

    private void initViews() {
        my_coupon_use_rules = ((TextView) findViewById(R.id.my_coupon_use_rules));
        mListView = ((MyListView) findViewById(R.id.my_coupon_list));
        emptyView = ((TextView) findViewById(R.id.emptyView));
        mListView.setEmptyView(emptyView);
        buttomTag = ((TextView) findViewById(R.id.buttomTag));
        mPtrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptrClassicFrameLayout));

        String okinfo = "没有更多可用优惠券了  <font color=\"#f55404\" >查看过期券 >></font>";
        buttomTag.setText(Html.fromHtml(okinfo));
    }

    @Override
    public void onSucessView(MyCoupon myCoupon) {
        list = myCoupon.data.list;
        if (isOutOfData) {
            mAdapter.setCouponType("1");
        } else {
            mAdapter.setCouponType("2");
        }
        if (page == 1) {
            mAdapter.setData(list);
        } else {
            if (page <= Integer.parseInt(myCoupon.data.page.page_count)) {
                mAdapter.addDatas(list);
            }
        }
        page++;
        if (page > Integer.parseInt(myCoupon.data.page.page_count)) {
            buttomTag.setVisibility(View.VISIBLE);
        }
        mPtrFrame.refreshComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_coupon_use_rules:
                Intent intent = new Intent(this, AvCommdWebView.class);
                intent.putExtra("title", "使用规则");
                intent.putExtra("url", RequestUrl.getRequestPath(RequestUrl.SubPaths.couponRule));
                startActivity(intent);
                break;
            case R.id.buttomTag:
                mAdapter.clearData();  //清除数据
                page = 1;
                if (!isOutOfData) {
                    mPresenter.getCouponList("1", page + "");
                    String okinfo = "没有更多可用优惠券了  <font color=\"#f55404\" >查看过期券 >></font>";
                    buttomTag.setText(Html.fromHtml(okinfo));
                } else {  //过期
                    mPresenter.getCouponList("2", page + "");
                    String okinfo = "<font color=\"#f55404\" >查看可用券 >></font>";
                    buttomTag.setText(Html.fromHtml(okinfo));
                }
                isOutOfData = !isOutOfData;
                break;
        }
    }
}
