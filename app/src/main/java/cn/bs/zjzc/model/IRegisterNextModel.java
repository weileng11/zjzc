package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.bean.RegisterRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FinishRegisterResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IRegisterNextModel extends IBaseModel {
    void register(RegisterRequestBody registerRequestBody, HttpTaskCallback<FinishRegisterResponse.DataBean> callback);
}
