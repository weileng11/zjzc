package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.UserDataResponse;

/**
 * Created by Ming on 2016/6/8.
 */
public interface ILoginView extends IBaseView {
    boolean isRememberPassword();

    void startHomeActivity();

    void setAccount(String account);

    void setPassword(String password);

    void setRememberPassword(boolean isRemember);

}
