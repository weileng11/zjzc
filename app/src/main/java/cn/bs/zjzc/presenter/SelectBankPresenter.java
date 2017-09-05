package cn.bs.zjzc.presenter;

import java.util.List;

import cn.bs.zjzc.model.ISelectBankModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.SelectBankModel;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.ui.view.ISelectBankView;

/**
 * Created by Ming on 2016/6/15.
 */
public class SelectBankPresenter {
    private ISelectBankView mSelectBankView;
    private ISelectBankModel mSelectBankModel;

    public SelectBankPresenter(ISelectBankView selectBankView) {
        mSelectBankView = selectBankView;
        mSelectBankModel = new SelectBankModel();
        //
    }

    public void getBankList() {
        mSelectBankModel.getBankList(new HttpTaskCallback<List<BankListResponse.DataBean>>() {
            @Override
            public void onTaskFailed(String errorInfo) {

            }

            @Override
            public void onTaskSuccess(List<BankListResponse.DataBean> data) {
                mSelectBankView.showBankList(data);
            }
        });
    }
}
