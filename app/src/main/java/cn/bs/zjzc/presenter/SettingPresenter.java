package cn.bs.zjzc.presenter;

import java.util.zip.InflaterOutputStream;

import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.ISettingModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.LoginModel;
import cn.bs.zjzc.model.impl.SettingModel;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.ui.view.ISettingView;
import cn.bs.zjzc.util.SPUtils;

/**
 * Created by Ming on 2016/6/8.
 */
public class SettingPresenter {

    private ISettingView mSettingView;
    private ISettingModel mSettingModel;
    private ILoginModel mLoginModel;

    public SettingPresenter(ISettingView settingView) {
        mSettingView = settingView;
        mSettingModel = new SettingModel();
        mLoginModel = new LoginModel(mSettingView.getContext());
    }

    public void logout() {
        mSettingView.showLoading("正在注销");
        mSettingModel.logout(new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                showLogout(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                showLogout("退出成功");
            }
        });
    }

    private void showLogout(String info) {
        mSettingView.hideLoading();
        mSettingView.showMsg(info);
        mSettingView.logout();
    }

    public void getAppVersionInfo() {
        mSettingModel.getAppVersionInfo(new HttpTaskCallback<AppVersionInfo>() {
            @Override
            public void onTaskFailed(String errorInfo) {
            }

            @Override
            public void onTaskSuccess(AppVersionInfo data) {
                mSettingView.getVersionInfoSuccess(data);
            }
        });
    }


}
