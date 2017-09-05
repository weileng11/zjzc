package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FAQListResponse;

/**
 * Created by Ming on 2016/7/7.
 */
public interface IServiceHelpModel extends IBaseModel {
    void getFAQList(String keyword, String page, HttpTaskCallback<FAQListResponse.DataBean> callback);
}
