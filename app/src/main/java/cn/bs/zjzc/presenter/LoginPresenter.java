package cn.bs.zjzc.presenter;


import android.util.Log;

import cn.bs.zjzc.model.IUserModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.LoginModel;
import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.impl.UserModel;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.ui.view.ILoginView;

/**
 * Created by Ming on 2016/6/3.
 */
public class LoginPresenter {

    private ILoginView mLoginView;
    private ILoginModel mLoginModel;

    public LoginPresenter(ILoginView loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModel(mLoginView.getContext());
    }

    public void checkLoginStatus() {
        //检查是否已经登录过,如果已经登录过就打开主页.
        if (mLoginModel.isLogin()) {
            //更新用户数据
            mLoginModel.updateUserInfo();
            //进入主页
            mLoginView.startHomeActivity();
        }
    }

    public void checkRememberPassword() {
        //自动填充帐号
       // mLoginView.setAccount(mLoginModel.getLastLoginUser());
        //如果用户上次登录勾选记住密码,则自动填充密码
        if (mLoginModel.isRememberPassword()) {
            mLoginView.setPassword(mLoginModel.getPassword());
            mLoginView.setRememberPassword(true);
        }
    }

    public void login(String account, final String password) {
        mLoginView.showLoading("登录中...");
        mLoginModel.login(account, password, new HttpTaskCallback<LoginResponse>() {

            @Override
            public void onTaskFailed(String errorInfo) {
                mLoginView.hideLoading();
                mLoginView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(LoginResponse data) {
                mLoginView.hideLoading();
                if (mLoginView.isRememberPassword()) {
                    //保存密码
                    mLoginModel.savePassword(password);
                } else {
                    mLoginModel.removePassword();
                }
                //更新用户数据
                mLoginModel.updateUserInfo();
                //进入主页
                mLoginView.startHomeActivity();
            }
        });
    }


}
