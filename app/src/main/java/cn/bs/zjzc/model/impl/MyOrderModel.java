package cn.bs.zjzc.model.impl;

import cn.bs.zjzc.model.IMyOrderModel;
import cn.bs.zjzc.model.bean.OrderListRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.OrderListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/29.
 */
public class MyOrderModel implements IMyOrderModel {


    @Override
    public void getOrderList(final OrderListRequestBody requestBody, final HttpTaskCallback<OrderListResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderList);
        PostHttpTask<OrderListResponse> httpTask = new PostHttpTask<>(url);
        httpTask.params(requestBody)
                .execute(new GsonCallback<OrderListResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderListResponse response) {
                        callback.onTaskSuccess(response.data);
                    }
                });
    }
}
