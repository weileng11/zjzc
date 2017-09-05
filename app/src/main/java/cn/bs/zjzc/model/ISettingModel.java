package cn.bs.zjzc.model;

import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.BaseResponse;

/**
 * Created by yiming on 2016/6/20.
 */
public interface ISettingModel extends IBaseModel{
    void logout(HttpTaskCallback<BaseResponse> callback);

    void getAppVersionInfo(HttpTaskCallback<AppVersionInfo> versionInfoHttpTaskCallback);
}
