package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IMyWalletModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.MyWalletModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.MyWalletResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IMyWalletView;

/**
 * Created by Ming on 2016/6/14.
 */
public class MyWalletPresenter {

    private IMyWalletView mMyWalletView;
    private IMyWalletModel mMyWalletModel;

    public MyWalletPresenter(IMyWalletView myWalletView) {
        mMyWalletView = myWalletView;
        mMyWalletModel = new MyWalletModel();
    }

    public void getWalletInfo() {
        mMyWalletView.showLoading("正在获取钱包信息...");
        mMyWalletModel.getWalletInfo(new HttpTaskCallback<MyWalletResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mMyWalletView.hideLoading();
                mMyWalletView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(MyWalletResponse.DataBean data) {
                mMyWalletView.hideLoading();
                mMyWalletView.showWalletInfo(data);
            }
        });
    }
}
