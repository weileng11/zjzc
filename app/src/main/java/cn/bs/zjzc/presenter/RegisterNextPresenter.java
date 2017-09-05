package cn.bs.zjzc.presenter;


import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.IRegisterNextModel;
import cn.bs.zjzc.model.bean.RegisterRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.LoginModel;
import cn.bs.zjzc.model.impl.RegisterNextModel;
import cn.bs.zjzc.model.response.FinishRegisterResponse;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.ui.view.IRegisterNextView;

/**
 * Created by Ming on 2016/6/7.
 */
public class RegisterNextPresenter {

    private IRegisterNextView mRegisterNextView;
    private IRegisterNextModel mRegisterNextModel;
    private ILoginModel mLoginModel;


    public RegisterNextPresenter(IRegisterNextView iRegisterNextView) {
        mRegisterNextView = iRegisterNextView;
        mRegisterNextModel = new RegisterNextModel();
        mLoginModel = new LoginModel(mRegisterNextView.getContext());
    }

    public void register(final RegisterRequestBody registerRequestBody) {
        mRegisterNextView.showLoading("注册中...");
        mRegisterNextModel.register(registerRequestBody, new HttpTaskCallback<FinishRegisterResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mRegisterNextView.hideLoading();
                mRegisterNextView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(FinishRegisterResponse.DataBean data) {
                //注册成功自动登录
                login(registerRequestBody.acount, registerRequestBody.passwd);
            }
        });

    }

    private void login(final String account, String password) {
        mLoginModel.login(account, password, new HttpTaskCallback<LoginResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mRegisterNextView.hideLoading();
                //登录失败返回登录界面
                mRegisterNextView.startLoginActivity();
            }

            @Override
            public void onTaskSuccess(LoginResponse data) {
                mRegisterNextView.hideLoading();
                //登录成功进入主页
                mRegisterNextView.startHomeActivity();
            }
        });
    }


}
