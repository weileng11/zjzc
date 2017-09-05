package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.*;

/**
 * Created by Ming on 2016/6/4.
 */
public interface IRegisterView extends IBaseView {
    void startCountDownTimer();

    void next(String account, String token);
}