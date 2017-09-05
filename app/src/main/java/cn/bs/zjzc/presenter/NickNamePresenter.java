package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.INickNameModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.NickNameModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.INickNameView;

/**
 * Created by Ming on 2016/6/13.
 */
public class NickNamePresenter {
    private INickNameView mNickNameView;
    private INickNameModel mNickNameModel;

    public NickNamePresenter(INickNameView nickNameView) {
        mNickNameView = nickNameView;
        mNickNameModel = new NickNameModel();
    }

    public void changeNickName(String name) {
        mNickNameView.showLoading("正在保存...");
        mNickNameModel.changeNickName(name, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mNickNameView.hideLoading();
                mNickNameView.showMsg(errorInfo);
                mNickNameView.finish();
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mNickNameView.hideLoading();
                mNickNameView.showMsg(data.errinfo);
                mNickNameView.changeSuccess();
            }
        });
    }
}
