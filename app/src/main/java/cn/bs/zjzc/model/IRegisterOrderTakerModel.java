package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.bean.RegisterOrderTakerRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IRegisterOrderTakerModel extends IBaseModel {
    void registerOrderTaker(RegisterOrderTakerRequestBody requestBody, HttpTaskCallback<BaseResponse> callback);

}
