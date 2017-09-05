package cn.bs.zjzc.model;


import android.content.Context;
import android.content.SharedPreferences;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.SPUtils;

/**
 * Created by Ming on 2016/6/3.
 */
public interface ILoginModel extends IBaseModel {
    boolean isLogin();

    boolean isRememberPassword();

    void savePassword(String password);

    void removePassword();

    String getPassword();

    String getLastLoginUser();

    void login(String account, String password, HttpTaskCallback<LoginResponse> callback);

    void updateUserInfo();
}
