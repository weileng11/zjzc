package cn.bs.zjzc.model;

import android.content.SharedPreferences;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.App;
import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IUserModel extends IBaseModel {

    void updateUserInfo();

    void setUserInfoFromLocal();

    void setUserInfo(UserDataResponse.DataBean data);

    void saveUserInfo(UserDataResponse.DataBean data);
}
