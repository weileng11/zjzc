package cn.bs.zjzc.presenter;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOftenAddressModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OftenAddressModel;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;
import cn.bs.zjzc.ui.view.IOftenUseAddressView;

/**
 * Created by Ming on 2016/7/2.
 */
public class OftenUseAddressPresenter {
    private IOftenUseAddressView mOftenUseAddressView;
    private IOftenAddressModel mOftenAddressModel;

    public OftenUseAddressPresenter(IOftenUseAddressView oftenUserAddressView) {
        mOftenUseAddressView = oftenUserAddressView;
        mOftenAddressModel = new OftenAddressModel(mOftenUseAddressView.getContext(), App.LOGIN_USER.getAccount());
    }

    public void getHomeAddress() {
        mOftenUseAddressView.showLoading();
        mOftenAddressModel.getHomeAddress(new HttpTaskCallback<OftenUseAddressResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mOftenUseAddressView.hideLoading();
                mOftenUseAddressView.hideHomeAddress();
            }

            @Override
            public void onTaskSuccess(OftenUseAddressResponse.DataBean data) {
                mOftenUseAddressView.hideLoading();
                mOftenUseAddressView.setHomeAddress(data);
            }
        });
    }

    public void getCompanyAddress() {
        mOftenUseAddressView.showLoading();
        mOftenAddressModel.getCompanyAddress(new HttpTaskCallback<OftenUseAddressResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mOftenUseAddressView.hideLoading();
                mOftenUseAddressView.hideCompanyAddress();
            }

            @Override
            public void onTaskSuccess(OftenUseAddressResponse.DataBean data) {
                mOftenUseAddressView.hideLoading();
                mOftenUseAddressView.setCompanyAddress(data);
            }
        });
    }
}
