package cn.bs.zjzc.presenter;

import android.text.TextUtils;

import cn.bs.zjzc.model.IRegisterModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.RegisterModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.VerifyCodeResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IRegisterView;
import cn.bs.zjzc.util.DensityUtils;
import okhttp3.Request;

/**
 * Created by Ming on 2016/6/4.
 */
public class RegisterPresenter {
    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;

    public RegisterPresenter(IRegisterView iRegisterView) {
        mRegisterView = iRegisterView;
        mRegisterModel = new RegisterModel();
    }

    public void getVerificationCode(String phone) {
        mRegisterModel.getCode(phone, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {

            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                //请求成功开始倒计时
                mRegisterView.startCountDownTimer();
            }
        });
    }

    public void next(final String account, final String code) {
        mRegisterView.showLoading("正在校验验证码");
        mRegisterModel.next(account, code, new HttpTaskCallback<VerifyCodeResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mRegisterView.showMsg(errorInfo);
                mRegisterView.hideLoading();
            }

            @Override
            public void onTaskSuccess(VerifyCodeResponse.DataBean data) {
                //进行下一步注册
                mRegisterView.hideLoading();
                mRegisterView.next(account, data.token);
            }
        });
    }


}
