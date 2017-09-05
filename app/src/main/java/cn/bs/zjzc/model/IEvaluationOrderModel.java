package cn.bs.zjzc.model;

import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderEvaluation;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IEvaluationOrderModel {

    void commintEvaluationOrder(String type, String order_id, String evaluate_type, String level, String content, HttpTaskCallback<BaseResponse> callback);

    void checkOrderEvaluation(String type, String order_id, HttpTaskCallback<OrderEvaluation> callback);
}
