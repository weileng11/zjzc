package cn.bs.zjzc.model;

import java.util.List;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.NewsListResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface INewsCenterModel extends IBaseModel{
    void getNewsList(String page, String number, HttpTaskCallback<NewsListResponse.DataBean> callback);

    void setNewsRead(String id, HttpTaskCallback<BaseResponse> callback);

    void deleteNews(String id, HttpTaskCallback<BaseResponse> callback);
}
