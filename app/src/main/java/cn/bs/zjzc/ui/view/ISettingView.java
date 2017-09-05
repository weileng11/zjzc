package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.AppVersionInfo;

/**
 * Created by Ming on 2016/6/8.
 */
public interface ISettingView extends IBaseView {
    void logout();

    void getVersionInfoSuccess(AppVersionInfo data);
}
