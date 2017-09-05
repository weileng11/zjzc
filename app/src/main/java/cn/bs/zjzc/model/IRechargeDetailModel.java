package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.RechargeDetailResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IRechargeDetailModel extends IBaseModel{
    void getRechargeDetail(String p, HttpTaskCallback<RechargeDetailResponse.DataBean> callback);
}
