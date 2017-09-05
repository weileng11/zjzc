package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IMyOrderModel;
import cn.bs.zjzc.model.bean.OrderListRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.MyOrderModel;
import cn.bs.zjzc.model.response.OrderListResponse;
import cn.bs.zjzc.fragment.IMyOrderView;

/**
 * Created by Ming on 2016/6/29.
 */
public class MyOrderPresenter {
    private IMyOrderView mMyOrderView;
    private IMyOrderModel mMyOrderModel;

    public MyOrderPresenter(IMyOrderView myOrderView) {
        mMyOrderView = myOrderView;
        mMyOrderModel = new MyOrderModel();
    }

    public void getOrderList(OrderListRequestBody requestBody) {
        mMyOrderModel.getOrderList(requestBody, new HttpTaskCallback<OrderListResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mMyOrderView.completeFresh();
                mMyOrderView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(OrderListResponse.DataBean data) {
                mMyOrderView.completeFresh();
                mMyOrderView.showOrderList(data);
            }
        });
    }
}
