package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.PointDetailResponse;

/**
 * Created by Ming on 2016/7/11.
 */
public interface IMyPointView extends IBaseView {
    void completeFresh();

    void showPointList(PointDetailResponse.DataBean data);
}
