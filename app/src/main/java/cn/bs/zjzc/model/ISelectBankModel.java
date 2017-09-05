package cn.bs.zjzc.model;

import java.util.List;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BankListResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface ISelectBankModel extends IBaseModel {
    void getBankList(HttpTaskCallback<List<BankListResponse.DataBean>> callback);
}
