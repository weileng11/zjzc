package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IRechargeDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.RechargeDetailModel;
import cn.bs.zjzc.model.response.RechargeDetailResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IRechargeDetailView;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargeDetailPresenter {
    private IRechargeDetailView mRechargeDetailView;
    private IRechargeDetailModel mRechargeDetailModel;


    public RechargeDetailPresenter(IRechargeDetailView rechargeView) {
        mRechargeDetailView = rechargeView;
        mRechargeDetailModel = new RechargeDetailModel();
    }

    public void getRechargeDetail(String p) {
        mRechargeDetailModel.getRechargeDetail(p, new HttpTaskCallback<RechargeDetailResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {

            }

            @Override
            public void onTaskSuccess(RechargeDetailResponse.DataBean data) {
                mRechargeDetailView.showRechargeDetail(data);
            }
        });
    }
}
