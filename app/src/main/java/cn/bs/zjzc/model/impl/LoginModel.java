package cn.bs.zjzc.model.impl;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IUserModel;
import cn.bs.zjzc.model.bean.User;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.SPUtils;

/**
 * Created by Ming on 2016/6/3.
 */
public class LoginModel implements ILoginModel {
    private Context mContext;
    private SharedPreferences sp;
    private IUserModel mUserModel;

    public LoginModel(Context context) {
        sp = context.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        mContext = context;
    }

    @Override
    public boolean isLogin() {
        //App初始化了登录帐号为最后登录帐号,如果帐号为空,返回false
        if (TextUtils.isEmpty(App.LOGIN_USER.getAccount())) {
            return false;
        }
        if (!App.LOGIN_USER.isLogin()) {
            return false;
        }
        //token为空,无法登录,返回false
        if (TextUtils.isEmpty(App.LOGIN_USER.getToken())) {
            return false;
        }
        return true;
    }

    /**
     * 是否记住密码
     *
     * @return
     */
    @Override
    public boolean isRememberPassword() {
        return App.LOGIN_USER.isRememberPassword();
    }

    /**
     * 保存密码
     *
     * @param password
     */
    @Override
    public void savePassword(String password) {
        App.LOGIN_USER.savePassword(password);
    }


    @Override
    public void removePassword() {
        App.LOGIN_USER.removePassword();
    }

    @Override
    public String getPassword() {
        return App.LOGIN_USER.getPassword();
    }

    @Override
    public String getLastLoginUser() {
        return sp.getString("last_login", "");
    }

    @Override
    public void login(final String account, final String password, final HttpTaskCallback<LoginResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.loginPath);
        PostHttpTask<LoginResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("acount", account)
                .addParams("passwd", password)
                .execute(new GsonCallback<LoginResponse>() {
                    @Override
                    public void onFailed(String info) {
                        callback.onTaskFailed(info);
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        sp.edit().putString("last_login", account).commit();
                        App.LOGIN_USER = new User(mContext, account);
                        App.LOGIN_USER.login(response.data);
                        callback.onTaskSuccess(response);
                    }
                });
    }

    //请求网络更新用户数据
    @Override
    public void updateUserInfo() {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getUserInfo);
        PostHttpTask<UserDataResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<UserDataResponse>() {
                    @Override
                    public void onFailed(String info) {
                    }

                    @Override
                    public void onSuccess(UserDataResponse response) {
                        App.LOGIN_USER.updateUserInfo(response.data);
                    }
                });
    }

}
