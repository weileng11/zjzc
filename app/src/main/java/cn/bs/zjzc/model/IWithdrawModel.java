package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.WithdrawResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IWithdrawModel extends IBaseModel{
    void withdraw(String password, String money, HttpTaskCallback<WithdrawResponse> callback);
}
