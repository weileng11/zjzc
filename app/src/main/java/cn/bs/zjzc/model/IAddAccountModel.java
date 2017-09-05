package cn.bs.zjzc.model;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/15.
 */
public interface IAddAccountModel extends IBaseModel {
    void addAccount(BankListResponse.DataBean bankData, String account, String name, HttpTaskCallback<BankAccountInfoResponse.DataBean> callback);
}
