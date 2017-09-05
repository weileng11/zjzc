package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;

/**
 * Created by Ming on 2016/7/6.
 */
public interface IFundDetailModel extends IBaseModel{
    void getFundDetail(String p, HttpTaskCallback<FundDetailResponse.DataBean> callback);
}
