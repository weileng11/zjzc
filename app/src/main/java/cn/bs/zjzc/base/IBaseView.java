package cn.bs.zjzc.base;

import android.content.Context;

/**
 * Created by Ming on 2016/6/4.
 */
public interface IBaseView {
    void showLoading();

    void showLoading(String msg);

    void hideLoading();

    void showMsg(String msg);

    void finish();

    Context getContext();

}
