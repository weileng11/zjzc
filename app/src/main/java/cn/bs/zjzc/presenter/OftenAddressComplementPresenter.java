package cn.bs.zjzc.presenter;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IOftenAddressModel;
import cn.bs.zjzc.model.bean.EditAddressRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OftenAddressModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.ui.view.IOftenAddressComplementView;

/**
 * Created by Ming on 2016/6/28.
 */
public class OftenAddressComplementPresenter {
    private IOftenAddressComplementView mInfoComplementView;
    private IOftenAddressModel mInfoComplementModel;

    public OftenAddressComplementPresenter(IOftenAddressComplementView infoComplementView) {
        mInfoComplementView = infoComplementView;
        mInfoComplementModel = new OftenAddressModel(mInfoComplementView.getContext(), App.LOGIN_USER.getAccount());
    }

    public void editOftenAddress(EditAddressRequestBody requestBody, String city) {
        mInfoComplementView.showLoading();
        mInfoComplementModel.editOftenAddress(requestBody, city, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mInfoComplementView.hideLoading();
                mInfoComplementView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mInfoComplementView.hideLoading();
                mInfoComplementView.showEditSuccess();
            }
        });
    }
}
