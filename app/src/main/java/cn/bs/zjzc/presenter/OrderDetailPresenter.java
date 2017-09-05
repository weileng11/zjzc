package cn.bs.zjzc.presenter;

import android.widget.TextView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.base.IBasePresenter;
import cn.bs.zjzc.model.IOrderDetailModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OderDetailModer;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IOrderDetailView;
import cn.bs.zjzc.util.CheckCodeTimer;

/**
 * Created by Administrator on 2016/6/29.
 */
public class OrderDetailPresenter implements IBasePresenter {
    private IOrderDetailView mOrderDetailView;
    private IOrderDetailModel mOrderDetailModel;

    public OrderDetailPresenter(IOrderDetailView mOrderDetailView) {
        this.mOrderDetailView = mOrderDetailView;
        mOrderDetailModel = new OderDetailModer();
    }

    public void getOrderDetail(String orderType, String order_id) {
        mOrderDetailView.showLoading("数据加载中...");
        mOrderDetailModel.getOrderDetail(orderType, order_id, new HttpTaskCallback<OrderDetail>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mOrderDetailView.showMsg(errorInfo);
                mOrderDetailView.hideLoading();
            }

            @Override
            public void onTaskSuccess(OrderDetail data) {
                mOrderDetailView.showData(data);
                mOrderDetailView.hideLoading();
            }
        });
    }

    public void confirmTakeGoogs(String orderType, String order_id) {
        mOrderDetailView.showLoading("确认中...");
        mOrderDetailModel.confirmTakeGoods(orderType, order_id, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mOrderDetailView.showMsg(errorInfo);
                mOrderDetailView.hideLoading();
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mOrderDetailView.confirmTakeSuccess();
                mOrderDetailView.hideLoading();
            }
        });
    }

    /**
     * 验证完成订单，获取验证码
     *
     * @param order_id 订单id
     * @param phone    接收验证码的手机
     * @param v        倒计时的TextView
     */
    public void getCode(String order_id, String phone, final TextView v) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.sendCode);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<BaseResponse>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("order_id", order_id)
                .addParams("phone", phone)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        mOrderDetailView.showMsg(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        new CheckCodeTimer(v).start();
                    }
                });
    }

    /**
     * 完成订单验证
     *
     * @param order_id 订单id
     * @param isWaimai 是否是外卖(如果为外卖，则code可以为空)
     * @param code     验证码
     */
    public void orderCompleted(String order_id, boolean isWaimai, String code) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.finishOrder);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<BaseResponse>(url);
        if (!isWaimai) {  //不是外卖
            httpTask.addParams("token", App.LOGIN_USER.getToken())
                    .addParams("order_id", order_id)
                    .addParams("code", code)
                    .execute(new GsonCallback<BaseResponse>() {
                        @Override
                        public void onFailed(String errorInfo) {
                            mOrderDetailView.showMsg(errorInfo);
                            mOrderDetailView.orderCompletedSuccess();
                        }

                        @Override
                        public void onSuccess(BaseResponse response) {
                            mOrderDetailView.showMsg(response.errinfo);
                            mOrderDetailView.orderCompletedSuccess();
                        }
                    });
        } else {
            httpTask.addParams("token", App.LOGIN_USER.getToken())
                    .addParams("order_id", order_id)
                    .execute(new GsonCallback<BaseResponse>() {
                        @Override
                        public void onFailed(String errorInfo) {
                            mOrderDetailView.showMsg(errorInfo);
                            mOrderDetailView.orderCompletedSuccess();
                        }

                        @Override
                        public void onSuccess(BaseResponse response) {
                            mOrderDetailView.showMsg(response.errinfo);
                            mOrderDetailView.orderCompletedSuccess();
                        }
                    });
        }
    }

}
