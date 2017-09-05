package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IWithdrawDetailModel extends IBaseModel {
    void getWithdrawDetail(String p, HttpTaskCallback<WithdrawDetailResponse.DataBean> callback);
}
