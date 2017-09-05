package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.PointDetailResponse;

/**
 * Created by Ming on 2016/7/11.
 */
public interface IMyPointModel extends IBaseModel {

    void getPointList(String page, HttpTaskCallback<PointDetailResponse.DataBean> callback);
}
