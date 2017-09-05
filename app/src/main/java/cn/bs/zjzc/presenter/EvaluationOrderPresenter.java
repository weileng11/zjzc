package cn.bs.zjzc.presenter;


import cn.bs.zjzc.model.IEvaluationOrderModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.EvaluationOrderModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderEvaluation;
import cn.bs.zjzc.ui.view.IOrderEvaluationView;

/**
 * Created by Ming on 2016/6/3.
 */
public class EvaluationOrderPresenter {

    private IOrderEvaluationView mEvaluationView;
    private IEvaluationOrderModel mEvaluationOrderModel;

    public EvaluationOrderPresenter(IOrderEvaluationView orderEvaluationView) {
        mEvaluationView = orderEvaluationView;
        mEvaluationOrderModel = new EvaluationOrderModel();
    }

    public void conmintEvaluation(String type, String order_id, String evaluate_type, String level, String content) {
        mEvaluationView.showLoading("提交中...");
        mEvaluationOrderModel.commintEvaluationOrder(type, order_id, evaluate_type, level, content, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mEvaluationView.showMsg(errorInfo);
                mEvaluationView.hideLoading();
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mEvaluationView.showMsg(data.errinfo);
                mEvaluationView.evaluationSuccessView();
                mEvaluationView.hideLoading();
            }
        });
    }


    public void checkOrderEvaluation(String type, String order_id) {
        mEvaluationView.showLoading("加载中...");
        mEvaluationOrderModel.checkOrderEvaluation(type, order_id, new HttpTaskCallback<OrderEvaluation>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mEvaluationView.showMsg(errorInfo);
                mEvaluationView.hideLoading();
            }

            @Override
            public void onTaskSuccess(OrderEvaluation data) {
                mEvaluationView.checkOutEvaluation(data);
                mEvaluationView.hideLoading();
            }
        });
    }
}
