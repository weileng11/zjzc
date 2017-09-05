package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.EvaluationListResponse;

/**
 * Created by Ming on 2016/7/5.
 */
public interface IMyEvaluationModel extends IBaseModel {
    void getEvaluationList(String type, String page, HttpTaskCallback<EvaluationListResponse.DataBean> callback);
}
