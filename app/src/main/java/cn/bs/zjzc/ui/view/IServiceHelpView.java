package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.FAQListResponse;

/**
 * Created by Ming on 2016/7/7.
 */
public interface IServiceHelpView extends IBaseView {
    void showFAQList(FAQListResponse.DataBean data);

    void completeFresh();
}
