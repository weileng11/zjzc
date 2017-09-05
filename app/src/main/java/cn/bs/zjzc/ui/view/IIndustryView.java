package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;

/**
 * Created by Ming on 2016/6/13.
 */
public interface IIndustryView extends IBaseView {
    void showIndustry(List<String> data);

    void changeIndustrySuccess();
}
