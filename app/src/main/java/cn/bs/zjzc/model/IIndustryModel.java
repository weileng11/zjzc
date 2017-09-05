package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.IndustryResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IIndustryModel extends IBaseModel{
    void getIndustryList(HttpTaskCallback<IndustryResponse> callback);

    void changeIndustry(String industry, HttpTaskCallback<BaseResponse> callback);
}
