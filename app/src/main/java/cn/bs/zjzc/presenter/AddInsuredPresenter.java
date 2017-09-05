package cn.bs.zjzc.presenter;

import cn.bs.zjzc.App;
import cn.bs.zjzc.model.IAddInsuredModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.AddInsuredModel;
import cn.bs.zjzc.model.response.AddInsuredResponse;
import cn.bs.zjzc.ui.view.IAddInsuredView;

/**
 * 添加保价费计算方式
 * Created by mgc on 2016/7/1.
 */
public class AddInsuredPresenter {

    private IAddInsuredModel model;
    private IAddInsuredView view;

    public AddInsuredPresenter(IAddInsuredView view) {
        this.view = view;
        model = new AddInsuredModel();
    }

    public void getAddInsuredWay() {
        view.showLoading();
        model.getInsured_way(new HttpTaskCallback<AddInsuredResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                view.hideLoading();
                view.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(AddInsuredResponse.DataBean data) {
                App.LOGIN_USER.setData(data);
                view.hideLoading();
                view.getInsuredWaySuccess(data);
            }
        });
    }
}
