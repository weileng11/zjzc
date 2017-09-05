package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface ICompanyJobModel extends IBaseModel {

    void changeCompanyJob(String company_job, HttpTaskCallback<BaseResponse> callback);
}
