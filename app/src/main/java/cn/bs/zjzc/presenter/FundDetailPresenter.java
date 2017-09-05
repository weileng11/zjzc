package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IFundDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.FundDetailModel;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.ui.view.IFundDetailView;

/**
 * Created by Ming on 2016/7/6.
 */
public class FundDetailPresenter {
    private IFundDetailView mFundDetailView;
    private IFundDetailModel mFundDetailModel;

    public FundDetailPresenter(IFundDetailView fundDetailView) {
        mFundDetailView = fundDetailView;
        mFundDetailModel = new FundDetailModel();
    }

    public void getFundDetail(String page) {
        mFundDetailModel.getFundDetail(page, new HttpTaskCallback<FundDetailResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mFundDetailView.completeFresh();
                mFundDetailView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(FundDetailResponse.DataBean data) {
                mFundDetailView.completeFresh();
                mFundDetailView.showFundDetail(data);
            }
        });
    }
}
