package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.FeedbackListResponse;

/**
 * Created by Ming on 2016/6/14.
 */
public interface IFeedbackListView extends IBaseView {
    void showFeedbackList(List<FeedbackListResponse.DataBean> datas);
}
