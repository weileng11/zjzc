package cn.bs.zjzc.fragment;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.EvaluationListResponse;

/**
 * Created by Ming on 2016/7/5.
 */
public interface IMyEvaluationView {
    void showEvaluationList(EvaluationListResponse.DataBean data);

    void showMsg(String msg);

    void completeFresh();
}
