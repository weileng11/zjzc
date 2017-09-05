package cn.bs.zjzc.model;

import android.app.WallpaperInfo;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.MyWalletResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface IMyWalletModel extends IBaseModel {
    void getWalletInfo(HttpTaskCallback<MyWalletResponse.DataBean> callback);
}
