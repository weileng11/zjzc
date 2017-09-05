package cn.bs.zjzc.model;

import cn.bs.zjzc.App;
import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.bean.EditAddressRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/29.
 */
public interface IOftenAddressModel extends IBaseModel {
    void getOftenAddress(String type, HttpTaskCallback<OftenUseAddressResponse.DataBean> callback);

    void editOftenAddress(EditAddressRequestBody requestBody, String city, HttpTaskCallback<BaseResponse> callback);

    void saveAddress(EditAddressRequestBody addressBody, String city);

    void getHomeAddress(HttpTaskCallback<OftenUseAddressResponse.DataBean> callback);

    void getCompanyAddress(HttpTaskCallback<OftenUseAddressResponse.DataBean> callback);
}
