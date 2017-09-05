package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.OrderEvaluation;

/**
 * Created by Ming on 2016/6/13.
 */
public interface IOrderEvaluationView extends IBaseView {
    void evaluationSuccessView();

    void checkOutEvaluation(OrderEvaluation orderEvaluation);
}
