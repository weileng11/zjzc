package cn.bs.zjzc.model;

import java.util.List;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.FeedbackListResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IFeedbackListModel extends IBaseModel {
    void getFeedbackList(String page, String number, HttpTaskCallback<List<FeedbackListResponse.DataBean>> callback);

}
