package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IOrderSettingModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OrderSettingModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderSetting;
import cn.bs.zjzc.ui.view.IOrderSettingView;
import cn.bs.zjzc.util.L;

/**
 * Created by Administrator on 2016/6/22.
 */
public class OrderSettingPresenter {

    private IOrderSettingView mIOrderSettingView;
    private IOrderSettingModel mOrderSettingModel;

    public OrderSettingPresenter(IOrderSettingView mIOrderSettingView) {
        this.mIOrderSettingView = mIOrderSettingView;
        mOrderSettingModel = new OrderSettingModel();
    }

    public void saveOrderSetting(String orderType, String range, String isFlush, String acceptType, String isPush) {
        mOrderSettingModel.saveSetting(orderType, range, isFlush, acceptType, isPush, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mIOrderSettingView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mIOrderSettingView.saveOrderSettingSuccess();
                mIOrderSettingView.showMsg(data.errinfo);
            }
        });
    }

    public void getOrderSetting() {
        mIOrderSettingView.showLoading("读取数据...");
        mOrderSettingModel.getSetting(new HttpTaskCallback<OrderSetting>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mIOrderSettingView.showMsg(errorInfo);
                mIOrderSettingView.hideLoading();
            }

            @Override
            public void onTaskSuccess(OrderSetting data) {
                mIOrderSettingView.getOrderSettingSuccess(data);
                mIOrderSettingView.hideLoading();
            }
        });
    }

}
