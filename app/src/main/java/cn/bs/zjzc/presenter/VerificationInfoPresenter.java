package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IVerificationInfoModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.VerificationInfoModel;
import cn.bs.zjzc.model.response.VerificationInfoResponse;
import cn.bs.zjzc.ui.view.IVerificationInfoView;

/**
 * Created by Ming on 2016/6/14.
 */
public class VerificationInfoPresenter {

    private IVerificationInfoView mVerificationInfoView;
    private IVerificationInfoModel mVerificationInfoModel;

    public VerificationInfoPresenter(IVerificationInfoView verificationInfoView) {
        mVerificationInfoView = verificationInfoView;
        mVerificationInfoModel = new VerificationInfoModel();
    }

    public void getVerificationInfo() {
        mVerificationInfoModel.getVerificationInfo(new HttpTaskCallback<VerificationInfoResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {

            }

            @Override
            public void onTaskSuccess(VerificationInfoResponse.DataBean data) {
                mVerificationInfoView.showVerificationInfo(data);
            }
        });
    }
}
