package cn.bs.zjzc.presenter;


import java.io.File;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IPersonalInfoModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.PersonalInfoModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IPersonalInfoView;

/**
 * Created by Ming on 2016/6/13.
 */
public class PersonalInfoPresenter {
    private IPersonalInfoView mPersonalInfoView;
    private IPersonalInfoModel mPersonalInfoModel;

    public PersonalInfoPresenter(IPersonalInfoView personalInfoView) {
        mPersonalInfoView = personalInfoView;
        mPersonalInfoModel = new PersonalInfoModel();
    }


    public void changeHeader(File file) {
        mPersonalInfoView.showLoading("正在保存...");
        mPersonalInfoModel.changeHeader(file, new HttpTaskCallback<BaseResponse>() {

            @Override
            public void onTaskFailed(String errorInfo) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.setHeader();
            }
        });
    }

    public void changeGender(final String gender) {
        mPersonalInfoView.showLoading("正在保存...");
        mPersonalInfoModel.changeGender(gender, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.setGender(gender);
            }
        });


    }

    public void changeAge(final String age) {
        mPersonalInfoView.showLoading("正在保存...");
        mPersonalInfoModel.changeAge(age, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mPersonalInfoView.hideLoading();
                mPersonalInfoView.setAge(age);
            }
        });


    }
}
