package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IForgotPasswordModel extends IBaseModel{
    void recoverPassword(String phone, String code, String passwd, String rePasswd, HttpTaskCallback<BaseResponse> callback);

    void getCode(String phone, String type, HttpTaskCallback<BaseResponse> callback);
}
