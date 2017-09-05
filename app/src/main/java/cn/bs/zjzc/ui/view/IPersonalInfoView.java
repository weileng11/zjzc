package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.UserDataResponse;

/**
 * Created by Ming on 2016/6/13.
 */
public interface IPersonalInfoView extends IBaseView {
    void setHeader();

    void setGender(String gender);

    void setAge(String age);
}
