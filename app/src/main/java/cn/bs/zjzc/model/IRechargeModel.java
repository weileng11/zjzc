package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.RechargeResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IRechargeModel extends IBaseModel{
    void recharge(String type, String money, HttpTaskCallback<RechargeResponse.DataBean> callback);
}
