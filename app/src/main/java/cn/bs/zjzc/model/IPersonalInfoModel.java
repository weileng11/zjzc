package cn.bs.zjzc.model;

import java.io.File;

import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public interface IPersonalInfoModel {
    void changeHeader(File headerFile, HttpTaskCallback<BaseResponse> taskCallback);

    void changeGender(String gender, HttpTaskCallback<BaseResponse> callback);

    void changeAge(String age, HttpTaskCallback<BaseResponse> callback);

}
