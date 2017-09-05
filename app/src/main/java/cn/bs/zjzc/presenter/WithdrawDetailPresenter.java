package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IWithdrawDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.WithdrawDetailModel;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;
import cn.bs.zjzc.ui.view.IWithdrawDetailView;

/**
 * Created by Ming on 2016/6/16.
 */
public class WithdrawDetailPresenter {
    private IWithdrawDetailView mWithdrawDetailView;
    private IWithdrawDetailModel mWithdrawDetailModel;

    public WithdrawDetailPresenter(IWithdrawDetailView rechargeView) {
        mWithdrawDetailView = rechargeView;
        mWithdrawDetailModel = new WithdrawDetailModel();
    }

    public void getWithdrawDetail(String p) {
        mWithdrawDetailModel.getWithdrawDetail(p, new HttpTaskCallback<WithdrawDetailResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mWithdrawDetailView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(WithdrawDetailResponse.DataBean data) {
                mWithdrawDetailView.showWithdrawDetail(data);
            }
        });
    }
}
