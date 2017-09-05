package cn.bs.zjzc.presenter;


import cn.bs.zjzc.model.ICancelOrderModel;
import cn.bs.zjzc.model.ILoginModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.CancelOrderModel;
import cn.bs.zjzc.model.impl.LoginModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.ui.view.ICancelOrderView;
import cn.bs.zjzc.ui.view.ILoginView;

/**
 * Created by Ming on 2016/6/3.
 */
public class CancelOrderPresenter {

    private ICancelOrderView mCancelOrderView;
    private ICancelOrderModel mCancelOrderModel;

    public CancelOrderPresenter(ICancelOrderView cancelOrderView) {
        mCancelOrderView = cancelOrderView;
        mCancelOrderModel = new CancelOrderModel();
    }

    public void cancelOrder(String type, String order_id, String reason) {
        mCancelOrderView.showLoading("提交中...");
        mCancelOrderModel.cancelOrder(type, order_id, reason, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mCancelOrderView.showMsg(errorInfo);
                mCancelOrderView.hideLoading();
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mCancelOrderView.orderSuccessView();
                mCancelOrderView.showMsg(data.errinfo);
                mCancelOrderView.hideLoading();
            }
        });
    }


}
