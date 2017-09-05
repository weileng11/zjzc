package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AddInsuredResponse;

/**
 * 添加保价费计算方式
 * Created by mgc on 2016/7/1.
 */
public interface IAddInsuredModel extends IBaseModel{

    void getInsured_way(HttpTaskCallback<AddInsuredResponse.DataBean> httpTaskCallback);
}
