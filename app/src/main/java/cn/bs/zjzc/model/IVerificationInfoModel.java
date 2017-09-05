package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.VerificationInfoResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface IVerificationInfoModel extends IBaseModel {
    void getVerificationInfo(HttpTaskCallback<VerificationInfoResponse.DataBean> callback);
}
