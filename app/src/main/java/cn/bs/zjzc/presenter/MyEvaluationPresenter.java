package cn.bs.zjzc.presenter;

import android.util.Log;

import cn.bs.zjzc.model.IMyEvaluationModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.MyEvaluationModel;
import cn.bs.zjzc.model.response.EvaluationListResponse;
import cn.bs.zjzc.fragment.IMyEvaluationView;

/**
 * Created by Ming on 2016/7/5.
 */
public class MyEvaluationPresenter {
    private IMyEvaluationView mMyEvaluationView;
    private IMyEvaluationModel mMyEvaluationModel;

    public MyEvaluationPresenter(IMyEvaluationView myEvaluationView) {
        mMyEvaluationView = myEvaluationView;
        mMyEvaluationModel = new MyEvaluationModel();
    }

    public void getEvaluationList(String type, String page) {
        Log.i("getEvaluationList", "getEvaluationList: " + "");
        mMyEvaluationModel.getEvaluationList(type, page, new HttpTaskCallback<EvaluationListResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mMyEvaluationView.completeFresh();
                mMyEvaluationView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(EvaluationListResponse.DataBean data) {
                mMyEvaluationView.completeFresh();
                mMyEvaluationView.showEvaluationList(data);
            }
        });
    }

}
