package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.MyCouponModel;
import cn.bs.zjzc.model.response.IMyCouponModel;
import cn.bs.zjzc.model.response.MyCoupon;
import cn.bs.zjzc.ui.view.IMyCouponView;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MyCouponPresenter {
    private IMyCouponView mCouponView;
    private IMyCouponModel mCouponModel;

    public MyCouponPresenter(IMyCouponView myCouponView) {
        this.mCouponView = myCouponView;
        this.mCouponModel = new MyCouponModel();
    }

    public void getCouponList(String type, String page) {
        mCouponView.showLoading("数据加载中...");
        mCouponModel.getCouponList(type, page, new HttpTaskCallback<MyCoupon>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mCouponView.hideLoading();
                mCouponView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(MyCoupon data) {
                mCouponView.hideLoading();
                mCouponView.onSucessView(data);
            }
        });
    }
}
