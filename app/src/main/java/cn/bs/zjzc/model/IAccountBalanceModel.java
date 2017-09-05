package cn.bs.zjzc.model;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IAccountBalanceModel extends IBaseModel {

    void getBalanceInfo(HttpTaskCallback<BalanceResponse.DataBean> callback);

    void getBankAccountInfo(HttpTaskCallback<BankAccountInfoResponse.DataBean> callback);

}
