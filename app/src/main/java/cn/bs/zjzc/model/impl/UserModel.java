package cn.bs.zjzc.model.impl;

import android.content.Context;
import android.content.SharedPreferences;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IUserModel;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by yiming on 2016/6/20.
 */
public class UserModel implements IUserModel {
    private Context mContext;
    private SharedPreferences sp;

    public UserModel(Context context) {
        sp = context.getSharedPreferences("zjzc_data", Context.MODE_PRIVATE);
    }

    //请求网络更新用户数据
    public void updateUserInfo() {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getUserInfo);
        PostHttpTask<UserDataResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .execute(new GsonCallback<UserDataResponse>() {
                    @Override
                    public void onFailed(String info) {
                        setUserInfoFromLocal();
                    }

                    @Override
                    public void onSuccess(UserDataResponse response) {
                        setUserInfo(response.data);
                        saveUserInfo(response.data);
                    }
                });
    }

    //从本地提取用户信息
    public void setUserInfoFromLocal() {
        App.LOGIN_USER.setPhone(sp.getString("phone", ""));
        App.LOGIN_USER.setName(sp.getString("name", ""));
        App.LOGIN_USER.setMoney(sp.getString("money", ""));
        App.LOGIN_USER.setPoints(sp.getString("points", ""));
        App.LOGIN_USER.setAge(sp.getString("age", ""));
        App.LOGIN_USER.setSex(sp.getString("sex", ""));
        App.LOGIN_USER.setCompany_job(sp.getString("company_job", ""));
        App.LOGIN_USER.setHead_img(sp.getString("head_img", ""));
        App.LOGIN_USER.setType(sp.getString("type", ""));
        App.LOGIN_USER.setState(sp.getString("state", ""));
        App.LOGIN_USER.setLevel(sp.getString("level", ""));
        App.LOGIN_USER.setIndustry(sp.getString("industry", ""));
        App.LOGIN_USER.setStar(sp.getString("star", "0"));
        App.LOGIN_USER.setApply_state(sp.getString("apply_state", ""));
    }

    //网络更新用户信息
    public void setUserInfo(UserDataResponse.DataBean data) {
        App.LOGIN_USER.setPhone(data.phone);
        App.LOGIN_USER.setName(data.name);
        App.LOGIN_USER.setMoney(data.money);
        App.LOGIN_USER.setPoints(data.points);
        App.LOGIN_USER.setAge(data.age);
        App.LOGIN_USER.setSex(data.sex);
        App.LOGIN_USER.setCompany_job(data.company_job);
        App.LOGIN_USER.setHead_img(data.head_img);
        App.LOGIN_USER.setType(data.type);
        App.LOGIN_USER.setState(data.state);
        App.LOGIN_USER.setLevel(data.level);
        App.LOGIN_USER.setIndustry(data.industry);
        App.LOGIN_USER.setStar(data.star);
        App.LOGIN_USER.setApply_state(data.apply_state);
    }

    //保存用户信息到本地
    public void saveUserInfo(UserDataResponse.DataBean data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", data.phone);
        editor.putString("name", data.name);
        editor.putString("money", data.money);
        editor.putString("points", data.points);
        editor.putString("age", data.age);
        editor.putString("sex", data.sex);
        editor.putString("company_job", data.company_job);
        editor.putString("head_img", data.head_img);
        editor.putString("type", data.type);
        editor.putString("state", data.state);
        editor.putString("level", data.level);
        editor.putString("industry", data.industry);
        editor.putString("star", data.star);
        editor.putString("apply_state", data.apply_state);
        editor.commit();
    }
}
