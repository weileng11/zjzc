package cn.bs.zjzc.presenter;


import android.text.TextUtils;

import cn.bs.zjzc.model.IForgotPasswordModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.ForgotPasswordModel;
import cn.bs.zjzc.model.impl.LoginModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IForgotPasswordView;
import cn.bs.zjzc.util.DensityUtils;

/**
 * Created by Ming on 2016/6/12.
 */
public class ForgotPasswordPresenter {

    private IForgotPasswordView mForgotPasswordView;
    private IForgotPasswordModel mForgotPasswordModel;

    public ForgotPasswordPresenter(IForgotPasswordView forgotPasswordView) {
        mForgotPasswordView = forgotPasswordView;
        mForgotPasswordModel = new ForgotPasswordModel();
    }

    public void recoverPassword(String phone, String code, String passwd, String rePasswd) {
        mForgotPasswordModel.recoverPassword(phone, code, passwd, rePasswd, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mForgotPasswordView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mForgotPasswordView.finish();
            }
        });
    }


    public void getVerificationCode(String phone, String type) {
        mForgotPasswordModel.getCode(phone, type, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mForgotPasswordView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mForgotPasswordView.startCountDownTimer();
            }
        });
    }
}
