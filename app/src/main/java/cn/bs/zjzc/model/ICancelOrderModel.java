package cn.bs.zjzc.model;

import java.util.Map;

import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.OrderRespose;
import cn.bs.zjzc.model.response.UserDataResponse;

/**
 * Created by Ming on 2016/6/20.
 */
public interface ICancelOrderModel {

    void cancelOrder(String type, String order_id, String reason, HttpTaskCallback<BaseResponse> callback);

}
