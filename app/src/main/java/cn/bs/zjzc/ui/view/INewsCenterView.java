package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.NewsListResponse;

/**
 * Created by Ming on 2016/6/17.
 */
public interface INewsCenterView extends IBaseView {
    void showNewsList(NewsListResponse.DataBean data);

    void deleteNews(int position);

    void refreshFail();
}
