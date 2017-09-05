package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.VerifyCodeResponse;

/**
 * Created by Ming on 2016/6/21.
 */
public interface IRegisterModel extends IBaseModel {
    void getCode(String phone, HttpTaskCallback<BaseResponse> callback);

    void next(String account, String code, HttpTaskCallback<VerifyCodeResponse.DataBean> callback);
}
