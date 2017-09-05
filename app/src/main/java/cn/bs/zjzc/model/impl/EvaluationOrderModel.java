package cn.bs.zjzc.model.impl;


import cn.bs.zjzc.App;
import cn.bs.zjzc.model.ICancelOrderModel;
import cn.bs.zjzc.model.IEvaluationOrderModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderEvaluation;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/3.
 */
public class EvaluationOrderModel implements IEvaluationOrderModel {
    @Override
    public void commintEvaluationOrder(String type, String order_id, String evaluate_type, String level, String content, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.orderEvaluation);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("evaluate_type", evaluate_type)
                .addParams("order_id", order_id)
                .addParams("level", level)
                .addParams("content", content)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }

    @Override
    public void checkOrderEvaluation(String type, String order_id, final HttpTaskCallback<OrderEvaluation> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.checkEvaluation);
        PostHttpTask<OrderEvaluation> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .addParams("order_id", order_id)
                .execute(new GsonCallback<OrderEvaluation>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OrderEvaluation response) {
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
